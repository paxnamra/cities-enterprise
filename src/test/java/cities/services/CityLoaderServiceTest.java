package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import cities.services.interfaces.ICityLoader;
import cities.services.interfaces.ICityReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CityLoaderServiceTest {

    private final String FULL_DATASET = "src/test/resources/data/full_dataset_cities.csv";
    private final String MEDIUM_DATASET = "src/test/resources/data/medium_dataset_cities.csv";
    private final String SMALL_DATASET = "src/test/resources/data/small_dataset_cities.csv";

    @MockBean
    private CityRepository repository;

    @MockBean
    private ICityReader readerService;

    @Autowired
    private ICityLoader cityLoader;

    private List<City> cities;

    @BeforeEach
    public void setup() {
        cities = new ArrayList<>();
    }

    @AfterEach
    public void clear() {
        cities.clear();
    }

    @Test
    public void loadCities_populatesValuesIntoDb() throws IOException {
        cities.addAll(List.of(
                new City(1L, "Wrocław", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Wroclaw-Rathaus.jpg/499px-Wroclaw-Rathaus.jpg"),
                new City(2L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"),
                new City(3L, "Los Angeles", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Wiki_training_0226.jpg/500px-Wiki_training_0226.jpg")
        ));

        when(repository.saveAll(any(List.class))).thenReturn(cities);
        when(readerService.readCitiesFrom(SMALL_DATASET)).thenReturn(cities);

        cityLoader.loadCities(SMALL_DATASET);

        verify(repository, times(2)).saveAll(anyList());
        assertEquals(3, cities.size());
    }
}
