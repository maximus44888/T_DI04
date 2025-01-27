package dam.alumno.filmoteca;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TableView<Pelicula> tableView;
    @FXML
    private TableColumn<Pelicula, Integer> IDColumn;
    @FXML
    private TableColumn<Pelicula, String> titleColumn;
    @FXML
    private TableColumn<Pelicula, Integer> yearColumn;
    @FXML
    private TableColumn<Pelicula, String> descriptionColumn;
    @FXML
    private TableColumn<Pelicula, Float> ratingColumn;
    @FXML
    private TableColumn<Pelicula, String> posterColumn;
    @FXML
    private HBox filmView;
    @FXML
    private FilmController filmViewController;

    @FXML
    private void initialize() {
        tableView.setItems(DatosFilmoteca.getInstancia().getListaPeliculas());
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
        posterColumn.setCellValueFactory(cellData -> cellData.getValue().posterProperty());

        filmViewController.filmProperty().bind(tableView.getSelectionModel().selectedItemProperty());
        if (!tableView.getItems().isEmpty()) {
            tableView.getSelectionModel().clearAndSelect(0);
        }
    }

    @FXML
    private void onAddFilm(final ActionEvent actionEvent) throws IOException {
        showAddUpdateView(State.ADD);
    }

    @FXML
    private void onUpdateFilm(final ActionEvent actionEvent) throws IOException {
        showAddUpdateView(State.UPDATE);
    }

    private void showAddUpdateView(final State state) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("add-update-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        final var controller = fxmlLoader.<AddUpdateController>getController();
        controller.stateProperty().set(state);
        final Pelicula viewFilm = switch (state) {
            case ADD:
                var nextId = tableView.getItems().stream().mapToInt(Pelicula::getId).max().orElse(0) + 1;
                yield new Pelicula(nextId, "", 0, "", 0, "");
            case UPDATE:
                var selectedFilm = tableView.getSelectionModel().getSelectedItem();
                yield new Pelicula(selectedFilm.getId(), selectedFilm.getTitle(), selectedFilm.getYear(), selectedFilm.getDescription(), selectedFilm.getRating(), selectedFilm.getPoster());
            case SHOW:
                yield tableView.getSelectionModel().getSelectedItem();
        };
        controller.filmProperty().set(viewFilm);
        Stage stage = new Stage();
        stage.setTitle(state.value);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onRemoveFilm(final ActionEvent actionEvent) {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onClose(final ActionEvent actionEvent) {
        tableView.getScene().getWindow().hide();
    }
}