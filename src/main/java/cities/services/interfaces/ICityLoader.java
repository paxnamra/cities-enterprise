package cities.services.interfaces;

/**
 * The ICityLoader interface defines the contract for loading city data from a file
 * and populating the database with the loaded city data.
 */
public interface ICityLoader {

    /**
     * Loads city data from the given file path and populates the database with the loaded city data.
     *
     * @param filePath String representing the file path of the city data file.
     */
    void loadCities(String filePath);
}
