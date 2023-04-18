package cities.services;

import cities.models.City;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityReaderService {
    public List<City> readCSVFrom(String filePath) throws IOException {
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

        return cities;
    }
}
