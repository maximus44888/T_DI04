package dam.alumno.filmoteca;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class AddUpdateController {

    private final ObjectProperty<Pelicula> film = new SimpleObjectProperty<>(new Pelicula());
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
        final var films = DatosFilmoteca.getInstancia().getListaPeliculas();
        switch (state.get()) {
            case ADD -> films.add(film.get());
            case UPDATE -> films.filtered(p -> p.getId() == film.get().getId()).stream().findFirst().ifPresent(p -> {
                p.setTitle(film.get().getTitle());
                p.setYear(film.get().getYear());
                p.setDescription(film.get().getDescription());
                p.setRating(film.get().getRating());
                p.setPoster(film.get().getPoster());
            });
        }
        display.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel(final ActionEvent actionEvent) {
        display.getScene().getWindow().hide();
    }

    public ObjectProperty<Pelicula> filmProperty() {
        return film;
    }

    public ObjectProperty<State> stateProperty() {
        return state;
    }
}