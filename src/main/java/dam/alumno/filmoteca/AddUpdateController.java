package dam.alumno.filmoteca;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class AddUpdateController {

    private final ObjectProperty<Film> film = new SimpleObjectProperty<>(new Film());
    private final ObjectProperty<State> state = new SimpleObjectProperty<>(State.SHOW);

    @FXML
    private Text display;
    @FXML
    private HBox filmView;
    @FXML
    private FilmController filmViewController;

    @FXML
    private void initialize() {
        display.textProperty().bind(Bindings.createStringBinding(() -> state.get().value, state));

        filmViewController.filmProperty().bindBidirectional(film);
        filmViewController.stateProperty().bind(state);
    }

    @FXML
    private void onAccept(final ActionEvent actionEvent) {
        final var films = FilmArchive.getInstance().films;
        switch (state.get()) {
            case ADD -> films.add(film.get());
            case UPDATE -> films.filtered(p -> p.id.get() == film.get().id.get()).stream().findFirst().ifPresent(p -> {
                p.title.set(film.get().title.get());
                p.year.set(film.get().year.get());
                p.description.set(film.get().description.get());
                p.rating.set(film.get().rating.get());
                p.poster.set(film.get().poster.get());
            });
        }
        display.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel(final ActionEvent actionEvent) {
        display.getScene().getWindow().hide();
    }

    public ObjectProperty<Film> filmProperty() {
        return film;
    }

    public ObjectProperty<State> stateProperty() {
        return state;
    }
}