package dam.alumno.filmoteca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilmArchive {
    private static FilmArchive instance;
    public final ObservableList<Pelicula> films;

    private FilmArchive() {
        films = FXCollections.observableArrayList();
    }

    public static FilmArchive getInstance() {
        if (instance == null) instance = new FilmArchive();
        return instance;
    }
}