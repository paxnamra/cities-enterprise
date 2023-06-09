package cities.controllers;

import cities.models.City;
import cities.services.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    private List<City> cities;

    @BeforeEach
    void setup() {
        cities = new ArrayList<>();

        cities.addAll(List.of(
                new City(1L, "Wrocław", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Wroclaw-Rathaus.jpg/499px-Wroclaw-Rathaus.jpg"),
                new City(2L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"),
                new City(3L, "Los Angeles", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Wiki_training_0226.jpg/500px-Wiki_training_0226.jpg")
        ));
    }

    @AfterEach
    void clear() {
        cities.clear();
    }

    @Test
    void getCities_returnsCitiesListWith3Items() throws Exception {
        when(cityService.getAllCities(0, 10)).thenReturn(cities);

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(cities.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(cities.get(0).getName()))
                .andExpect(jsonPath("$[0].imageLink").value(cities.get(0).getImageLink()));
    }

    @Test
    void getCity_returnsRequestedCity() throws Exception {
        City city = cities.get(1);
        String tokyo = city.getName();

        when(cityService.getCityByName(tokyo)).thenReturn(Optional.of(city));

        mockMvc.perform(get("/api/cities/{cityName}", city.getName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(city.getId()))
                .andExpect(jsonPath("$.name").value(city.getName()))
                .andExpect(jsonPath("$.imageLink").value(city.getImageLink()));
    }

    @Test
    void getCity_returnsNotFoundStatus() throws Exception {
        String cityName = "San Escobar";
        when(cityService.getCityByName(cityName)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cities/{pathName}", cityName))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCity_changesNameAndImageOfTheCity() throws Exception {
        City updatedCityData = new City(2L, "Neotokyo", "https://scandroid.com");
        String cityName = "Tokyo";

        when(cityService.updateCityNameAndImage(eq(cityName), any())).thenReturn(updatedCityData);

        mockMvc.perform(put("/api/cities/{cityName}/edit", cityName)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCityData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Neotokyo"))
                .andExpect(jsonPath("$.imageLink").value("https://scandroid.com"));
    }

}
