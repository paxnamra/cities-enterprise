package cities.services;

import cities.models.City;
import cities.services.interfaces.ICityReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * CityReader service class for reading city data from a file.
 */
@Service
public class CityReader implements ICityReader {
    private static final Logger LOG = LoggerFactory.getLogger(CityReader.class);

    public List<City> readCitiesFrom(String filePath) throws IOException {
        List<City> cities = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                Long id = Long.parseLong(fields[0]);
                String name = fields[1];
                String imageLink = fields[2];

                City city = new City(id, name, imageLink);
                cities.add(city);
            }
        }

        LOG.info("Successfully read data from: {}", filePath);
        return cities;
    }
}
