package dam.alumno.filmoteca;

import javafx.scene.image.Image;

public final class Utils {

    public static String boxFilm(final Film film) {
        final String title = "Película ID " + film.id.get();
        final String tabTitle = "│ " + title + " │";
        final String tabTop = "┌" + "─".repeat(title.length() + 2) + "┐";
        final String content = """
                Título: %s
                Año: %d
                Valoración: %.1f
                Cartel: %s
                Descripción: %s
                """.formatted(film.title.get(), film.year.get(), film.rating.get(), film.poster.get(), film.description.get());
        final int maxLength = content.lines().mapToInt(String::length).max().orElse(0);
        final String contentTop = "├" + "─".repeat(maxLength + 2) + "┐";
        final String contentBottom = "└" + "─".repeat(maxLength + 2) + "┘";
        final StringBuilder sb = new StringBuilder(tabTop).append("\n").append(tabTitle).append("\n").append(contentTop).append("\n");
        content.lines().forEachOrdered(line ->
                sb.append("│ ").append(line).append(" ".repeat(maxLength - line.length())).append(" │").append("\n")
        );
        sb.append(contentBottom);
        return sb.toString();
    }

    public static Image getNoImage(final String lang) {
        return new Image(MainApp.class.getResource(
                switch (lang) {
                    case String s when s.equals("en") -> "No_image_available_en.png";
                    default -> "No_image_available_es.png";
                }
        ).toString());
    }

    public static Image getDragAndDrop(final String lang) {
        return new Image(MainApp.class.getResource(
                switch (lang) {
                    case String s when s.equals("en") -> "Drag_and_drop_en.png";
                    default -> "Drag_and_drop_es.png";
                }
        ).toString());
    }
}
