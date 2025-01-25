package dam.alumno.filmoteca;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

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
    private void onAddFilm(final ActionEvent actionEvent) {
    }

    @FXML
    private void onUpdateFilm(final ActionEvent actionEvent) {
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