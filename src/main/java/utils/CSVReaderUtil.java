package utils;

import cities.models.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderUtil {
    public static List<City> readCSV(String filePath) {
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
        } catch (IOException errorMessage) {
            System.err.println("Missing cities file or wrong file under given filepath: " + errorMessage.getMessage());
        }

        return cities;
    }
}
