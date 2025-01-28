package dam.alumno.filmoteca;

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
        IDColumn.setCellValueFactory((final TableColumn.CellDataFeatures<Film, Integer> cellData) -> cellData.getValue().id.asObject());
        titleColumn.setCellValueFactory((final TableColumn.CellDataFeatures<Film, String> cellData) -> cellData.getValue().title);
        yearColumn.setCellValueFactory((final TableColumn.CellDataFeatures<Film, Integer> cellData) -> cellData.getValue().year.asObject());
        descriptionColumn.setCellValueFactory((final TableColumn.CellDataFeatures<Film, String> cellData) -> cellData.getValue().description);
        ratingColumn.setCellValueFactory((final TableColumn.CellDataFeatures<Film, Float> cellData) -> cellData.getValue().rating.asObject());
        posterColumn.setCellValueFactory((final TableColumn.CellDataFeatures<Film, String> cellData) -> cellData.getValue().poster);

        filmViewController.filmProperty().bind(tableView.getSelectionModel().selectedItemProperty());
        if (!tableView.getItems().isEmpty()) {
            tableView.getSelectionModel().clearAndSelect(0);
        }
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
                var nextId = tableView.getItems().stream().mapToInt(e -> e.id.get()).max().orElse(0) + 1;
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
        stage.show();
    }

    @FXML
    private void onRemoveFilm() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onClose() {
        tableView.getScene().getWindow().hide();
    }
}