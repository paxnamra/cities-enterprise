package cities;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CitiesApplication implements CommandLineRunner {

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
}

