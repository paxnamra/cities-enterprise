package cities.services.interfaces;

import cities.models.City;

import java.io.IOException;
import java.util.List;

public interface ICityReader {
    List<City> readCitiesFrom(String filePath) throws IOException;
}
