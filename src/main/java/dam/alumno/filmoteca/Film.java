package dam.alumno.filmoteca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {
    public final IntegerProperty id;
    public final StringProperty title;
    public final IntegerProperty year;
    public final StringProperty description;
    public final FloatProperty rating;
    public final StringProperty poster;

    @JsonCreator
    public Film(@JsonProperty("id") final int id,
                @JsonProperty("title") final String title,
                @JsonProperty("year") final int year,
                @JsonProperty("description") final String description,
                @JsonProperty("rating") final float rating,
                @JsonProperty("poster") final String poster) {
        this();
        this.id.set(id);
        this.title.set(title);
        this.year.set(year);
        this.description.set(description);
        this.rating.set(rating);
        this.poster.set(poster);
    }

    public Film(final int id) {
        this();
        this.id.set(id);
    }

    public Film(final Film film) {
        this();
        this.id.set(film.id.get());
        this.title.set(film.title.get());
        this.year.set(film.year.get());
        this.description.set(film.description.get());
        this.rating.set(film.rating.get());
        this.poster.set(film.poster.get());
    }

    public Film() {
        id = new SimpleIntegerProperty();
        title = new SimpleStringProperty();
        year = new SimpleIntegerProperty();
        description = new SimpleStringProperty();
        rating = new SimpleFloatProperty();
        poster = new SimpleStringProperty();
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title=" + title +
                ", year=" + year +
                ", description=" + description +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
