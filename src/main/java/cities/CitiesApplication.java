package cities;

import cities.services.CityLoaderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CitiesApplication implements CommandLineRunner {

    @Autowired
    private CityLoaderService cityLoader;

    public static void main(String[] args) {
        SpringApplication.run(CitiesApplication.class, args);
    }
    public String printWhenSpringStart() {
        return "Spring Application has just started!";
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(new CitiesApplication().printWhenSpringStart());
    }

    @PostConstruct
    public void loadData() {
        cityLoader.loadCitiesFromCSV("src/main/resources/data/full_dataset_cities.csv");
    }
}

