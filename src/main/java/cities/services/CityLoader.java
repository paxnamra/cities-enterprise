package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import cities.services.interfaces.ICityLoader;
import cities.services.interfaces.ICityReader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * CityLoader service class for loading cities from a file and saving them to the repository.
 */
@Service
public class CityLoader implements ICityLoader {
    private static final String FILE_PATH = "src/main/resources/data/full_dataset_cities.csv";
    private static final Logger LOG = LoggerFactory.getLogger(CityLoader.class);

    @Autowired
    private CityRepository repository;
    @Autowired
    private ICityReader cityReader;

    /**
     * Method called after the construction of the object to populate the database with city data.
     */
    @PostConstruct
    public void populateInDatabase() {
        LOG.info("Populating database... ");
        loadCities(FILE_PATH);
    }

    public void loadCities(String filePath) {
        try {
            List<City> cities = cityReader.readCitiesFrom(filePath);
            repository.saveAll(cities);
            LOG.info("Successfully populated database with given data.");
        } catch (IOException errorMessage) {
            LOG.error(String.format("%s - Missing cities file or wrong file under given filepath: %s", errorMessage, errorMessage.getMessage()));
        }
    }
}
