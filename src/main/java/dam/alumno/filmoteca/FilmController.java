package dam.alumno.filmoteca;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FilmController {

    private final ObjectProperty<Film> film = new SimpleObjectProperty<>(new Film());
    private final ObjectProperty<State> state = new SimpleObjectProperty<>(State.SHOW);


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
    private ImageView posterOutput;

    @FXML
    private void initialize() {
        ratingOutput.textProperty().bind(ratingInput.valueProperty().asString("%.1f"));
        posterOutput.imageProperty().bind(Bindings.createObjectBinding(() -> {
            try {
                return new Image(posterInput.getText());
            } catch (final Exception e) {
                return null;
            }
        }, posterInput.textProperty()));

        IDInput.setDisable(true);
        IDInput.setEditable(false);
        filmInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        yearInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        ratingInput.disableProperty().bind(Bindings.createBooleanBinding(() -> state.get() == State.SHOW, state));
        descriptionInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        posterInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));

        yearInput.setTextFormatter(new TextFormatter<>((change) -> {
            try {
                Integer.parseInt(change.getControlNewText());
                return change;
            } catch (final NumberFormatException e) {
                return null;
            }
        }));
        ratingInput.valueProperty().addListener((observable, previous, next) -> {
            if (next != null) {
                ratingInput.setValue(BigDecimal.valueOf(next.floatValue()).setScale(1, RoundingMode.HALF_UP).floatValue());
            }
        });

        film.addListener((observable, previous, next) -> {
            IDInput.textProperty().unbind();
            filmInput.textProperty().unbind();
            yearInput.textProperty().unbind();
            ratingInput.valueProperty().unbind();
            descriptionInput.textProperty().unbind();
            posterInput.textProperty().unbind();

            if (previous != null) {
                IDInput.textProperty().unbindBidirectional(previous.idProperty().asString());
                filmInput.textProperty().unbindBidirectional(previous.titleProperty());
                yearInput.textProperty().unbindBidirectional(previous.yearProperty());
                ratingInput.valueProperty().unbindBidirectional(previous.ratingProperty());
                descriptionInput.textProperty().unbindBidirectional(previous.descriptionProperty());
                posterInput.textProperty().unbindBidirectional(previous.posterProperty());
            }

            if (next == null) {
                next = new Film();
            }

            IDInput.textProperty().bind(next.idProperty().asString());
            filmInput.textProperty().bindBidirectional(next.titleProperty());
            Bindings.bindBidirectional(yearInput.textProperty(), next.yearProperty(), new StringConverter<>() {
                @Override
                public String toString(Number number) {
                    return number == null ? "" : Integer.toString((Integer) number);
                }

                @Override
                public Number fromString(String s) {
                    if (s == null) {
                        return null;
                    } else {
                        s = s.trim();
                        if (s.isEmpty()) {
                            return null;
                        }
                        try {
                            return Integer.valueOf(s);
                        } catch (final NumberFormatException e) {
                            return null;
                        }
                    }

                }
            });
            ratingInput.valueProperty().bindBidirectional(next.ratingProperty());
            descriptionInput.textProperty().bindBidirectional(next.descriptionProperty());
            posterInput.textProperty().bindBidirectional(next.posterProperty());
        });
    }

    public ObjectProperty<Film> filmProperty() {
        return film;
    }

    public ObjectProperty<State> stateProperty() {
        return state;
    }
}