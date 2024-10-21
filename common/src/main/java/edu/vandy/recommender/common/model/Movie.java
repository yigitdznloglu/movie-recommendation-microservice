package edu.vandy.recommender.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Movie title and vector as stored and returned from the database
 * microservice.
 */
@Entity
@Table(name = "MOVIE")
public class Movie
// Implement an interface that enables two Movie objects to be
// compared and checked for equality.
// TODO -- you fill in here
     implements Comparable<Movie>
{
    /**
     * The movie name.
     */
    @Id
    public String id;

    /**
     * The encoding of the movie properties.
     */
    @SuppressWarnings("JpaAttributeTypeInspection")
    public List<Double> vector;

    /**
     * A default constructor is needed.
     */
    public Movie() {
    }

    /**
     * Initialize field in a {@link Movie}.
     */
    public Movie(String id, List<Double> vector) {
        this.id = id;
        this.vector = vector;
    }

    /**
     * Create a {@link Movie} from a {@link Map.Entry}.
     *
     * @param movie The {@link Map.Entry}
     */
    public Movie(Map.Entry<String, List<Double>> movie) {
        id = movie.getKey();
        vector = movie.getValue();
    }

    /**
     * @return The title of the {@link Movie}
     */
    public String getTitle() {
        return id;
    }

    /**
     * Perform a case-insensitive comparison of this {@link Movie}
     * with the {@code other} {@link Movie} based on their IDs.
     *
     * @param other The {@link Movie} to compare to this {@link Movie}
     * @return A negative integer, zero, or a positive integer as this
     * movie's ID is less than, equal to, or greater than the
     * specified movie's ID (ignoring case)
     */
    // TODO -- you fill in here.
    @Override
    public int compareTo(Movie other) {
        // Create a null-safe string comparator that handles null IDs and performs case-insensitive comparison
        Comparator<String> nullSafeStringComparator = Comparator
                // Use nullsFirst to ensure that null IDs are considered less than non-null IDs
                .nullsFirst(String::compareToIgnoreCase);
        // Compare the IDs of this movie and the other movie using the null-safe string comparator
        return nullSafeStringComparator.compare(this.id, other.id);
    }

    /**
     * Overrides the {@code equals()} method to compare two {@link
     * Movie} objects based on their {@code id} only.
     *
     * @param object The other {@link Object} to compare with this
     *              object
     * @return true if the object ids are equal, false otherwise
     */
    // TODO -- you fill in here.
    @Override
    public boolean equals(Object object) {
        // Use pattern matching for instanceof to check and cast in one step, also handles the null check implicitly
        if (!(object instanceof Movie anotherMovie)) return false;

        // Check if the IDs are equal using Objects.equals to handle possible nulls safely
        return Objects.equals(this.id, anotherMovie.id);
    }

    /**
     * @return A hash of the {@link Movie} {@code id} (title)
     */
    // TODO -- you fill in here.
    @Override
    public int hashCode() {
        // Use Objects.hash to generate a hash code based on the id field
        return Objects.hash(id);
    }
}
