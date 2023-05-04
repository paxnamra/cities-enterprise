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
    private static final String CSV_HEADERS = "id,name,photo";
    private static final Logger LOG = LoggerFactory.getLogger(CityReader.class);

    public List<City> readCitiesFrom(String filePath) throws IOException {
        List<City> cities = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(CSV_HEADERS)) {
                    continue;
                }

                City city = parseCity(line);
                cities.add(city);
            }
        }

        LOG.info("Successfully read data from: {}", filePath);
        return cities;
    }

    /**
     * Parses a line from the city data file and creates a City object.
     *
     * @param line String representing a single line from the city data file.
     * @return A City object created from the given line.
     */
    private City parseCity(String line) {
        String[] fields = line.split(",");

        Long id = Long.parseLong(fields[0]);
        String name = fields[1];
        String imageLink = fields[2];

        return new City(id, name, imageLink);
    }
}
