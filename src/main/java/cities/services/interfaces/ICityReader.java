package cities.services.interfaces;

import cities.models.City;

import java.io.IOException;
import java.util.List;

/**
 * The ICityReader interface defines the contract for reading city data from a file
 * and converting it into a list of City objects.
 */
public interface ICityReader {

    /**
     * Reads city data from the specified file path and returns a list of City objects.
     *
     * @param filePath String representing the path of the file containing the city data.
     * @return List of City objects containing the data read from the file.
     * @throws IOException if an error occurs while reading the file.
     */
    List<City> readCitiesFrom(String filePath) throws IOException;
}
