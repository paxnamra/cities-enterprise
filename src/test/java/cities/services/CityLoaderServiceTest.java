package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import cities.services.interfaces.ICityReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityLoaderServiceTest {

    private final String FULL_DATASET = "src/test/resources/data/full_dataset_cities.csv";
    private final String MEDIUM_DATASET = "src/test/resources/data/medium_dataset_cities.csv";
    private final String SMALL_DATASET = "src/test/resources/data/small_dataset_cities.csv";

    @Mock
    private CityRepository repository;

    @Mock
    private ICityReader readerService;

    @InjectMocks
    private CityLoader cityLoader;

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
    void loadCities_populatesValuesIntoDb_whenFilePathIsCorrect() throws IOException {
        cities.addAll(List.of(
                new City(1L, "Wrocław", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Wroclaw-Rathaus.jpg/499px-Wroclaw-Rathaus.jpg"),
                new City(2L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"),
                new City(3L, "Los Angeles", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Wiki_training_0226.jpg/500px-Wiki_training_0226.jpg")
        ));

        when(repository.saveAll(any(List.class))).thenReturn(cities);
        when(readerService.readCitiesFrom(SMALL_DATASET)).thenReturn(cities);

        cityLoader.loadCities(SMALL_DATASET);

        verify(repository, times(1)).saveAll(anyList());
        assertEquals(3, cities.size());
    }

    @Test
    void loadCities_savesNothingIntoDb_whenFilePathIsIncorrect() throws IOException {
        String filePath = "some wrong path";
        IOException ioException = new IOException("Some error message");
        when(readerService.readCitiesFrom(filePath)).thenThrow(ioException);

        cityLoader.loadCities(filePath);

        verify(readerService).readCitiesFrom(filePath);
        verify(repository, never()).saveAll(anyList());
        assertEquals(0, cities.size());
    }
}
