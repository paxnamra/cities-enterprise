package cities.services;

import cities.models.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CityReaderServiceTest {

    private final String FULL_DATASET = "src/test/resources/data/full_dataset_cities.csv";
    private final String MEDIUM_DATASET = "src/test/resources/data/medium_dataset_cities.csv";
    private final String SMALL_DATASET = "src/test/resources/data/small_dataset_cities.csv";

    @InjectMocks
    private CityReader cityReader;

    private List<City> cities;

    @BeforeEach
    void setup() {
        cities = new ArrayList<>();
    }

    @AfterEach
    void clear() {
        cities.clear();
    }

    @Test
    void readCitiesFrom_returnsListWith1000Cities() throws IOException {
        cities.addAll(cityReader.readCitiesFrom(FULL_DATASET));
        assertEquals(1000, cities.size());
    }

    @Test
    void readCitiesFrom_returnsListWith13Cities() throws IOException {
        cities.addAll(cityReader.readCitiesFrom(MEDIUM_DATASET));
        assertEquals(13, cities.size());
    }

    @Test
    void readCitiesFrom_returnsListWith3Cities() throws IOException {
        cities.addAll(cityReader.readCitiesFrom(SMALL_DATASET));
        assertEquals(3, cities.size());
    }

    @Test
    void readCitiesFrom_throwsIOException_ifFilePathIsIncorrect() {
        String derpPath = "src/test/resources/data/non_existent_file.csv";

        assertThrows(IOException.class, () -> cityReader.readCitiesFrom(derpPath));
    }

    @Test
    void readCitiesFrom_throwsIOException_ifFilepathIsEmpty() {
        String derpPath = "";

        assertThrows(IOException.class, () -> cityReader.readCitiesFrom(derpPath));
    }
}
