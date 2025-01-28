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
    private HBox filmView;
    @FXML
    private FilmController filmViewController;

    @FXML
    private void initialize() {
        tableView.setItems(FilmArchive.getInstance().films);
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
        final Film viewFilm = switch (state) {
            case ADD:
                var nextId = tableView.getItems().stream().mapToInt(Film::getId).max().orElse(0) + 1;
                yield new Film(nextId, "", 0, "", 0, "");
            case UPDATE:
                var selectedFilm = tableView.getSelectionModel().getSelectedItem();
                yield new Film(selectedFilm.getId(), selectedFilm.getTitle(), selectedFilm.getYear(), selectedFilm.getDescription(), selectedFilm.getRating(), selectedFilm.getPoster());
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