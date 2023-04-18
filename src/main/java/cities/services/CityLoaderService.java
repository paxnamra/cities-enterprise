package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CityLoaderService {
    private static final String FILE_PATH = "src/main/resources/data/full_dataset_cities.csv";
    @Autowired
    private CityRepository repository;

    @Autowired
    private CityReaderService readerService;

    @PostConstruct
    public void populateInDatabase() {
        loadCities(FILE_PATH);
    }

    public void loadCities(String filePath) {
        try {
            List<City> cities = readerService.readCSVFrom(filePath);
            repository.saveAll(cities);
        } catch (IOException errorMessage) {
            System.err.println("Missing cities file or wrong file under given filepath: " + errorMessage.getMessage());
        }
    }
}
