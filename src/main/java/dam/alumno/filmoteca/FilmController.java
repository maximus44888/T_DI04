package dam.alumno.filmoteca;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FilmController {

    private final ObjectProperty<Pelicula> film = new SimpleObjectProperty<>(new Pelicula());

    @FXML
    private TextField IDInput;
    @FXML
    private TextField filmInput;
    @FXML
    private TextField yearInput;
    @FXML
    private Slider ratingInput;
    @FXML
    private Text ratingOutput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private TextField posterInput;
    @FXML
    public ImageView posterOutput;


    @FXML
    private void initialize() {
        ratingOutput.textProperty().bind(ratingInput.valueProperty().asString("%.2f"));
        posterOutput.imageProperty().bind(Bindings.createObjectBinding(() -> {
            try {
                return new Image(posterInput.getText());
            } catch (final Exception e) {
                return null;
            }
        }, posterInput.textProperty()));

        film.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                IDInput.textProperty().unbind();
                filmInput.textProperty().unbind();
                yearInput.textProperty().unbind();
                ratingInput.valueProperty().unbind();
                descriptionInput.textProperty().unbind();
                posterInput.textProperty().unbind();
            }

            if (newValue == null) {
                newValue = new Pelicula();
            }

            IDInput.textProperty().bind(newValue.idProperty().asString());
            filmInput.textProperty().bind(newValue.titleProperty());
            yearInput.textProperty().bind(newValue.yearProperty().asString());
            ratingInput.valueProperty().bind(newValue.ratingProperty());
            ratingInput.setDisable(false);
            descriptionInput.textProperty().bind(newValue.descriptionProperty());
            posterInput.textProperty().bind(newValue.posterProperty());

        });
    }

    public ObjectProperty<Pelicula> filmProperty() {
        return film;
    }
}
