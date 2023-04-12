package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static utils.CSVReaderUtil.readCSV;

@Service
public class CityLoaderService {
    @Autowired
    private CityRepository repository;

    @PostConstruct
    public void populateInDatabase() {
        loadCitiesFrom("src/main/resources/data/full_dataset_cities.csv");
    }

    public void loadCitiesFrom(String filePath) {
        List<City> cities = readCSV(filePath);
        repository.saveAll(cities);
    }


}
