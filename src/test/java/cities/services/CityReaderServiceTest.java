package cities.services;

import cities.models.City;
import cities.services.interfaces.ICityReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CityReaderServiceTest {

    private final String FULL_DATASET = "src/test/resources/data/full_dataset_cities.csv";
    private final String MEDIUM_DATASET = "src/test/resources/data/medium_dataset_cities.csv";
    private final String SMALL_DATASET = "src/test/resources/data/small_dataset_cities.csv";

    @Autowired
    private ICityReader cityReader;

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
    public void readCitiesFrom_returnsListWith1000Cities() throws IOException {
        cities.addAll(cityReader.readCitiesFrom(FULL_DATASET));
        assertEquals(1000, cities.size());
    }

    @Test
    public void readCitiesFrom_returnsListWith13Cities() throws IOException {
        cities.addAll(cityReader.readCitiesFrom(MEDIUM_DATASET));
        assertEquals(13, cities.size());
    }

    @Test
    public void readCitiesFrom_returnsListWith3Cities() throws IOException {
        cities.addAll(cityReader.readCitiesFrom(SMALL_DATASET));
        assertEquals(3, cities.size());
    }

    @Test
    public void readCitiesFrom_throwsException_ifFilePathIsIncorrect() {
        String derpPath = "src/test/resources/data/non_existent_file.csv";

        Assertions.assertThrows(IOException.class, () -> cityReader.readCitiesFrom(derpPath));
    }

    @Test
    public void readCitiesFrom_throwsException_ifFilepathIsEmpty() {
        String derpPath = "";

        Assertions.assertThrows(IOException.class, () -> cityReader.readCitiesFrom(derpPath));
    }
}
