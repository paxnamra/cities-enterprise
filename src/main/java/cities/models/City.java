package cities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The City class is a model representing a city with its identifier, name, and image link.
 * It is annotated as an entity for use with the database, and includes getters,
 * a no-argument constructor, and a constructor with all fields.
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class City {

    @Id
    private Long id;
    private String name;
    private String imageLink;
}

