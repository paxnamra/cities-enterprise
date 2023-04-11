package cities.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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

