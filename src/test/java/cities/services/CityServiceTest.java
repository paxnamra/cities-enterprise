package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    CityRepository repository;

    @InjectMocks
    CityService cityService;

    private List<City> cities;

    @BeforeEach
    void setup() {
        cities = new ArrayList<>();
    }

    @AfterEach
    void clear() {
        cities.clear();
    }

    @Test
    void getAllCities_returnsPaginatedListOfCities() {
        int page = 0;
        int size = 10;
        cities.addAll(List.of(
                new City(1L, "Wrocław", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Wroclaw-Rathaus.jpg/499px-Wroclaw-Rathaus.jpg"),
                new City(2L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"),
                new City(3L, "Los Angeles", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Wiki_training_0226.jpg/500px-Wiki_training_0226.jpg")
        ));
        Page<City> cityPage = new PageImpl<>(cities);
        when(repository.findAll(any(Pageable.class))).thenReturn(cityPage);

        List<City> paginatedCities = cityService.getAllCities(page, size);

        assertEquals(cities, paginatedCities);
        verify(repository).findAll(PageRequest.of(page, size));
    }

    @Test
    void getCityByName_returnsCityData() {
        String cityName = "Tokyo";
        City expectedCity = new City(1L, "Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg");
        when(repository.findCityByName(cityName)).thenReturn(Optional.of(expectedCity));

        Optional<City> city = cityService.getCityByName(cityName);

        assertEquals(Optional.of(expectedCity), city);
        verify(repository).findCityByName(cityName);
    }

    @Test
    void updateCityNameAndImage_updatesCityNameAndImage_whenCityExists() {
        String cityName = "Tokyo";
        City existingCity = new City(1L, cityName, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg");
        City newCityData = new City(1L, "Neotokyo", "https://scandroid-album.com");
        City expectedCity = new City(existingCity.getId(), newCityData.getName(), newCityData.getImageLink());

        when(repository.findCityByName(cityName)).thenReturn(Optional.of(existingCity));
        when(repository.save(argThat(city ->
                city.getName().equals(expectedCity.getName()) &&
                city.getImageLink().equals(expectedCity.getImageLink()))))
                .thenReturn(expectedCity);

        City updatedCity = cityService.updateCityNameAndImage(cityName, newCityData);

        assertEquals(expectedCity, updatedCity);
        verify(repository).findCityByName(cityName);
        verify(repository).save(argThat(city ->
                city.getName().equals(expectedCity.getName()) &&
                city.getImageLink().equals(expectedCity.getImageLink())));
    }

    @Test
    void updateCityNameAndImage_throwsNoSuchElementException_whenCityNotExists() {
        String cityName = "Khorinis";
        City newCityData = new City(1L, "The Colony", "https://gothic2/new-colony-image");

        when(cityService.getCityByName(cityName)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cityService.updateCityNameAndImage(cityName, newCityData));
    }
}