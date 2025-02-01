package dam.alumno.filmoteca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {
    public final IntegerProperty id;
    public final StringProperty title;
    public final IntegerProperty year;
    public final ListProperty<String> genre;
    public final StringProperty description;
    public final StringProperty director;
    public final FloatProperty rating;
    public final StringProperty poster;

    @JsonCreator
    public Film(@JsonProperty("id") final int id,
                @JsonProperty("title") final String title,
                @JsonProperty("year") final int year,
                @JsonProperty("genre") final List<String> genre,
                @JsonProperty("description") final String description,
                @JsonProperty("director") final String director,
                @JsonProperty("rating") final float rating,
                @JsonProperty("poster") final String poster) {
        this();
        this.id.set(id);
        this.title.set(title);
        this.year.set(year);
        this.genre.set(FXCollections.observableArrayList(genre));
        this.description.set(description);
        this.director.set(director);
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
        this.genre.set(film.genre.get());
        this.description.set(film.description.get());
        this.director.set(film.director.get());
        this.rating.set(film.rating.get());
        this.poster.set(film.poster.get());
    }

    public Film() {
        id = new SimpleIntegerProperty();
        title = new SimpleStringProperty();
        year = new SimpleIntegerProperty();
        genre = new SimpleListProperty<>(FXCollections.observableArrayList());
        description = new SimpleStringProperty();
        director = new SimpleStringProperty();
        rating = new SimpleFloatProperty();
        poster = new SimpleStringProperty();
    }

    @JsonGetter("id")
    public int getId() {
        return id.get();
    }

    @JsonGetter("title")
    public String getTitle() {
        return title.get();
    }

    @JsonGetter("year")
    public int getYear() {
        return year.get();
    }

    @JsonGetter("genre")
    public ObservableList<String> getGenre() {
        return genre.get();
    }

    @JsonGetter("description")
    public String getDescription() {
        return description.get();
    }

    @JsonGetter("director")
    public String getDirector() {
        return director.get();
    }

    @JsonGetter("rating")
    public float getRating() {
        return rating.get();
    }

    @JsonGetter("poster")
    public String getPoster() {
        return poster.get();
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title=" + title +
                ", year=" + year +
                ", genres=" + genre +
                ", description=" + description +
                ", director=" + director +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
