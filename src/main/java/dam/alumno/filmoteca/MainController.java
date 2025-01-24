package dam.alumno.filmoteca;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private void initialize() {
    }

    @FXML
    private void onAddFilm(final ActionEvent actionEvent) {
    }

    @FXML
    private void onUpdateFilm(final ActionEvent actionEvent) {
    }

    @FXML
    private void onRemoveFilm(final ActionEvent actionEvent) {
    }

    @FXML
    private void onClose(final ActionEvent actionEvent) {
    }
}