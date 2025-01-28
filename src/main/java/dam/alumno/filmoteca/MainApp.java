package dam.alumno.filmoteca;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(final Stage stage) throws IOException {
        final var fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        final var scene = new Scene(fxmlLoader.load());
        stage.setTitle("Filmoteca");
        stage.setScene(scene);
        stage.show();
    }

    public void init() {
        System.out.println("Cargando datos desde fichero datos/peliculas.json");
        final var films = FilmArchive.getInstance().films;
        final var objectMapper = new ObjectMapper();

        try {
            final var readFilms = objectMapper.readValue(
                    new File("datos/peliculas.json"),
                    new TypeReference<List<Pelicula>>() {}
            );

            films.setAll(readFilms);
        } catch (final IOException e) {
            System.out.println("ERROR al cargar los datos. La aplicación no puede iniciarse");
            e.printStackTrace();
            System.exit(1);
            return;
        }

        System.out.println(films);
    }

    public void stop() {
        final var films = FilmArchive.getInstance().films;
        System.out.println(films);
        final var objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File("datos/peliculas2.json"), films);
        } catch (final IOException e) {
            System.out.println("ERROR no se ha podido guardar los datos de la aplicación");
            e.printStackTrace();
        }
    }
}



