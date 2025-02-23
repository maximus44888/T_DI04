package dam.alumno.filmoteca;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;

public class FilmController {
    private final ObjectProperty<Film> film = new SimpleObjectProperty<>(new Film());
    private final ObjectProperty<State> state = new SimpleObjectProperty<>(State.SHOW);

    @FXML
    private TextField IDInput;
    @FXML
    private TextField filmInput;
    @FXML
    private TextField directorInput;
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
                final var image = new Image(posterInput.getText());
                if (image.isError()) throw new Exception();
                return image;
            } catch (final Exception e) {
                if (state.get() == State.SHOW) return Utils.getNoImage("es");
                return Utils.getDragAndDrop("es");
            }
        }, posterInput.textProperty()));

        IDInput.disableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        IDInput.setEditable(false);
        filmInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        directorInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        yearInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        ratingInput.disableProperty().bind(Bindings.createBooleanBinding(() -> state.get() == State.SHOW, state));
        descriptionInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));
        posterInput.editableProperty().bind(Bindings.createBooleanBinding(() -> state.get() != State.SHOW, state));

        yearInput.setTextFormatter(new TextFormatter<>((final TextFormatter.Change change) -> {
            try {
                Integer.parseInt(change.getControlNewText());
                return change;
            } catch (final NumberFormatException e) {
                return null;
            }
        }));
        ratingInput.valueProperty().addListener((final ObservableValue<? extends Number> observable, final Number previous, final Number next) -> {
            if (next != null) {
                ratingInput.setValue(BigDecimal.valueOf(next.floatValue()).setScale(1, RoundingMode.HALF_UP).floatValue());
            }
        });

        film.addListener((final ObservableValue<? extends Film> observable, final Film previous, Film next) -> {
            IDInput.textProperty().unbind();
            filmInput.textProperty().unbind();
            directorInput.textProperty().unbind();
            yearInput.textProperty().unbind();
            ratingInput.valueProperty().unbind();
            descriptionInput.textProperty().unbind();
            posterInput.textProperty().unbind();

            if (previous != null) {
                IDInput.textProperty().unbindBidirectional(previous.id.asString());
                filmInput.textProperty().unbindBidirectional(previous.title);
                directorInput.textProperty().unbindBidirectional(previous.director);
                yearInput.textProperty().unbindBidirectional(previous.year);
                ratingInput.valueProperty().unbindBidirectional(previous.rating);
                descriptionInput.textProperty().unbindBidirectional(previous.description);
                posterInput.textProperty().unbindBidirectional(previous.poster);
            }

            if (next == null) {
                next = new Film();
            }

            IDInput.textProperty().bind(next.id.asString());
            filmInput.textProperty().bindBidirectional(next.title);
            directorInput.textProperty().bindBidirectional(next.director);
            Bindings.bindBidirectional(yearInput.textProperty(), next.year, new StringConverter<>() {
                @Override
                public String toString(final Number number) {
                    return number == null ? "" : Integer.toString((Integer) number);
                }

                @Override
                public Number fromString(String s) {
                    if (s == null) return null;

                    s = s.trim();
                    if (s.isEmpty()) return null;

                    try {
                        return Integer.valueOf(s);
                    } catch (final NumberFormatException e) {
                        return null;
                    }
                }
            });
            ratingInput.valueProperty().bindBidirectional(next.rating);
            descriptionInput.textProperty().bindBidirectional(next.description);
            posterInput.textProperty().bindBidirectional(next.poster);
        });
    }

    @FXML
    private void onDragDropped(final DragEvent dragEvent) throws MalformedURLException {
        if (state.get() == State.SHOW) return;
        final var dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            posterInput.setText(dragboard.getFiles().getFirst().toURI().toURL().toString());
        } else if (dragboard.hasImage()) {
            posterInput.setText(dragboard.getImage().getUrl());
        }
    }

    @FXML
    private void onDragOver(final DragEvent dragEvent) {
        if (state.get() == State.SHOW) return;
        dragEvent.acceptTransferModes(TransferMode.LINK);
        dragEvent.consume();
    }

    public ObjectProperty<Film> filmProperty() {
        return film;
    }

    public ObjectProperty<State> stateProperty() {
        return state;
    }
}