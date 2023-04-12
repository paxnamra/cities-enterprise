package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import utils.CSVReaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CityLoaderServiceTest {

    @Autowired
    private CityLoaderService cityLoaderService;

    @MockBean
    private CityRepository repository;

    private List<City> cities;

    @BeforeEach
    public void setup() {
        cities = new ArrayList<>();
    }

    @Test
    public void loadCitiesFrom_populatesValuesIntoDb() {
        String filePath = "src/test/resources/data/small_dataset_cities.csv";
        cities.addAll(List.of(
                new City(1L, "Wrocław", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Wroclaw-Rathaus.jpg/499px-Wroclaw-Rathaus.jpg"),
                new City(2L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"),
                new City(3L, "Los Angeles", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Wiki_training_0226.jpg/500px-Wiki_training_0226.jpg")
        ));

        when(repository.saveAll(any(List.class))).thenReturn(cities);

        cityLoaderService.loadCitiesFrom(filePath);

        verify(repository, times(1)).saveAll(anyList());
        assertEquals(3, cities.size());
    }

    @Test
    public void readCSV_returnsListWith1000Cities() throws IOException {
        String filePath = "src/test/resources/data/full_dataset_cities.csv";
        cities.addAll(CSVReaderUtil.readCSV(filePath));

        assertEquals(1000, cities.size());
    }

    @Test
    public void readCSV_throwsException_ifFilePathIsIncorrect() {
        String derpPath = "src/test/resources/data/non_existent_file.csv";

        Assertions.assertThrows(IOException.class, () -> CSVReaderUtil.readCSV(derpPath));
    }

    @Test
    public void readCSV_throwsException_ifFilepathIsEmpty() {
        String derpPath = "";

        Assertions.assertThrows(IOException.class, () -> CSVReaderUtil.readCSV(derpPath));
    }
}
