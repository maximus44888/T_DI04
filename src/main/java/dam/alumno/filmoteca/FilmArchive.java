package dam.alumno.filmoteca;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilmArchive {
    private static FilmArchive instance;
    public final ObservableList<Pelicula> films;
    private final ObjectMapper objectMapper;

    private FilmArchive() {
        films = FXCollections.observableArrayList();
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static FilmArchive getInstance() {
        if (instance == null) instance = new FilmArchive();
        return instance;
    }

    public ObservableList<Pelicula> loadFrom(final File src) throws IOException {
        films.setAll(objectMapper.readValue(src, new TypeReference<List<Pelicula>>() {}));
        return films;
    }

    public void saveTo(final File dest) throws IOException {
        objectMapper.writeValue(dest, films);
    }
}