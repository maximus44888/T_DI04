package dam.alumno.filmoteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    private final File src = new File("datos/peliculas.json");
    private final File dest = new File("datos/peliculas2.json");

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
        System.out.println("Cargando datos desde " + src);

        try {
            System.out.println(FilmArchive.getInstance().loadFrom(src));
        } catch (final IOException e) {
            System.out.println("ERROR al cargar los datos. La aplicación no puede iniciarse");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        System.out.println("Guardando datos en " + dest);

        final var filmArchive = FilmArchive.getInstance();
        System.out.println(filmArchive.films);

        try {
            filmArchive.saveTo(dest);
        } catch (final IOException e) {
            System.out.println("ERROR no se ha podido guardar los datos de la aplicación");
            e.printStackTrace();
        }
    }
}



