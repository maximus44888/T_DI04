package dam.alumno.filmoteca;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TableView<Film> tableView;
    @FXML
    private TableColumn<Film, Integer> IDColumn;
    @FXML
    private TableColumn<Film, String> titleColumn;
    @FXML
    private TableColumn<Film, Integer> yearColumn;
    @FXML
    private TableColumn<Film, String> descriptionColumn;
    @FXML
    private TableColumn<Film, Float> ratingColumn;
    @FXML
    private TableColumn<Film, String> posterColumn;
    @FXML
    private FilmController filmViewController;
    @FXML
    public Button updateFilm;
    @FXML
    public Button removeFilm;
    @FXML
    public TextField searchInput;

    @FXML
    private void initialize() {
        final var filteredFilms = new FilteredList<>(FilmArchive.getInstance().films);
        filteredFilms.predicateProperty().bind(Bindings.createObjectBinding(() -> (final Film film) ->
                searchInput.getText().isBlank() || film.title.get().toLowerCase().contains(searchInput.getText().toLowerCase()), searchInput.textProperty()
        ));
        tableView.setItems(filteredFilms);

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        posterColumn.setCellValueFactory(new PropertyValueFactory<>("poster"));

        filmViewController.filmProperty().bind(tableView.getSelectionModel().selectedItemProperty());
        if (!tableView.getItems().isEmpty()) {
            tableView.getSelectionModel().clearAndSelect(0);
        }

        updateFilm.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        removeFilm.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void onAddFilm() throws IOException {
        showAddUpdateView(State.ADD);
    }

    @FXML
    private void onUpdateFilm() throws IOException {
        showAddUpdateView(State.UPDATE);
    }

    private void showAddUpdateView(final State state) throws IOException {
        final var fxmlLoader = new FXMLLoader(MainApp.class.getResource("add-update-view.fxml"));
        final var scene = new Scene(fxmlLoader.load());
        final var controller = fxmlLoader.<AddUpdateController>getController();
        controller.stateProperty().set(state);
        final var viewFilm = switch (state) {
            case ADD:
                var nextId = FilmArchive.getInstance().films.stream().mapToInt(e -> e.id.get()).max().orElse(0) + 1;
                yield new Film(nextId);
            case UPDATE:
                yield new Film(tableView.getSelectionModel().getSelectedItem());
            case SHOW:
                yield tableView.getSelectionModel().getSelectedItem();
        };
        controller.filmProperty().set(viewFilm);
        final var stage = new Stage();
        stage.setTitle(state.value);
        stage.setScene(scene);
        stage.initOwner(tableView.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void onRemoveFilm() {
        showAlert(
                Alert.AlertType.CONFIRMATION,
                "¿Estás seguro de que quieres eliminar la película seleccionada?",
                () -> FilmArchive.getInstance().films.remove(tableView.getSelectionModel().getSelectedItem())
        );
    }

    @FXML
    private void onClose() {
        showAlert(
                Alert.AlertType.CONFIRMATION,
                "¿Estás seguro de que quieres salir?",
                () -> tableView.getScene().getWindow().hide()
        );
    }

    private void showAlert(final Alert.AlertType alertType, final String contentText, final Runnable lambda) {
        new Alert(alertType, contentText).showAndWait().ifPresent((final ButtonType response) -> {
            if (response == ButtonType.OK) {
                lambda.run();
            }
        });
    }
}