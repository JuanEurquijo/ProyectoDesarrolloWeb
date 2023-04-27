import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.web.project.ProyectoWebApplication;
import org.web.project.model.Driver;
import org.web.project.repository.DriverRepository;

@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProyectoWebApplication.class)
public class DriverControllerIntegrationTest {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

    @LocalServerPort
    private int port;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    private TestRestTemplate rest;

    private List<Driver> drivers = new ArrayList<>();


    @BeforeEach
    void init() {
        Driver d1 = driverRepository.save(new Driver("Pepe", "Perez", "1234567890", "11111111111", "Calle 170"));
        Driver d2 = driverRepository.save(new Driver("Andres", "Perez", "0987654321", "2222222222", "Calle 180"));
        Driver d3 = driverRepository.save(new Driver("Enrique", "Perez", "2523261212", "3333333333", "Calle 190"));

        drivers.add(d1);
        drivers.add(d2);
        drivers.add(d3);
    }

 
    @Test
    void getAllDrivers() throws Exception{
        ResponseEntity<Driver[]> response = rest.getForEntity("http://localhost:" + port + "/api/driver/list", Driver[].class);
        System.out.println(drivers);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(drivers.toArray(), response.getBody());
    }

    @Test
    void findDriverById() throws Exception{
        ResponseEntity<Driver> response = rest.getForEntity("http://localhost:" + port + "/api/driver/1", Driver.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new Driver("Pepe", "Perez", "1234567890", "11111111111", "Calle 170"), response.getBody());
    }


    @Test
    void save() throws Exception {
        Driver driver = new Driver("Pepe", "Perez", "1234567890", "11111111111", "Calle 170");
        ResponseEntity<Driver> response = rest.postForEntity("http://localhost:" + port + "/api/driver", 
        driver, Driver.class);
        System.out.println(driver);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driver, response.getBody());
    }

    @Test
    void update() throws Exception {
        Driver driverToUpdate = new Driver("Julian", "Perez", "1234567890", "11111111111", "Calle 170");
        ResponseEntity<Driver> response = rest.exchange("http://localhost:" + port + "/api/driver/update/1",
        HttpMethod.PUT,
        new HttpEntity<>(driverToUpdate),
        Driver.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverToUpdate, response.getBody());
    }

    @Test
    void delete() throws Exception {
        Driver driver = driverRepository.save(new Driver("Pepe", "Perez", "1234567890", "11111111111", "Calle 170"));
        Long driverId = driver.getId();
        rest.delete("http://localhost:" + port + "/api/driver/delete/"+driverId);
        assertFalse(driverRepository.findById(driverId).isPresent());
    }
    
}
