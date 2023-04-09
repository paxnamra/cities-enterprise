package cities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CitiesApplicationTests {

    @Test
    void appPrintsOnStart() {
        CitiesApplication classUnderTest = new CitiesApplication();
        assertNotNull(classUnderTest.printWhenSpringStart(), "Spring Application has just started!");
    }

    @Test
    void contextLoads() {
    }

}
