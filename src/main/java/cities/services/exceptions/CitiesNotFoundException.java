package cities.services.exceptions;

public class CitiesNotFoundException extends RuntimeException {
    public CitiesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
