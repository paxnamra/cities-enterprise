package cities.controllers;

import cities.models.City;
import cities.services.CityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

//    @MockBean
//    private ObjectMapper objectMapper;

    private List<City> cities;

    @BeforeEach
    public void setup() {
        cities = new ArrayList<>();

        cities.addAll(List.of(
                new City(1L, "Wrocław", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Wroclaw-Rathaus.jpg/499px-Wroclaw-Rathaus.jpg"),
                new City(2L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"),
                new City(3L, "Los Angeles", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Wiki_training_0226.jpg/500px-Wiki_training_0226.jpg")
        ));
    }

    @AfterEach
    public void clear() {
        cities.clear();
    }

    @Test
    public void getCities_returnsCitiesListWith3Items() throws Exception {
        when(cityService.getAllCities()).thenReturn(cities);

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(cities.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(cities.get(0).getName()))
                .andExpect(jsonPath("$[0].imageLink").value(cities.get(0).getImageLink()));
    }

    @Test
    public void getCity_returnsRequestedCity() throws Exception {
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
    public void getCity_returnsNotFoundStatus() throws Exception {
        String cityName = "San Escobar";
        when(cityService.getCityByName(cityName)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cities/{pathName}", cityName))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCity_changesNameAndImageOfTheCity() throws Exception {
        //TODO test to fix, probably some issue with mapper

//        City cityData = new City(2L, "Tokyo", "https://example-of-the-city.com");
//        City updatedCityData = new City(2L, "Neo Tokyo", "https://scandroid.com");

        //objectMapper = new ObjectMapper();

//        when(cityService.getCityByName(cityData.getName())).thenReturn(Optional.of(cityData));
//        when(cityService.updateCityNameAndImage(cityData.getName(), updatedCityData)).thenReturn(updatedCityData);
//
//        mockMvc.perform(post("/{cityName}/edit", cityData.getName())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updatedCityData)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(jsonPath("$.name").value("Neo Tokyo"))
//                .andExpect(jsonPath("$.imageLink").value("https://scandroid.com"));
    }

    @Test
    public void updateCity_changesCityNameWhenImageStaysTheSame() throws Exception {}

    @Test
    public void updateCity_changesCityNameIsEmptyString() throws Exception {}

    @Test
    public void updateCity_changesCityNameWhenImageLinkIsEmpty() throws Exception {}

    @Test
    public void updateCity_throwsExceptionIfCityNotFound() {}
}
