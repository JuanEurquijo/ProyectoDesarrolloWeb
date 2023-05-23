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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.web.project.ProyectoWebApplication;
import org.web.project.model.Bus;
import org.web.project.repository.BusRepository;

@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProyectoWebApplication.class)
public class BusControllerIntegrationTest {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

    @LocalServerPort
    private int port;

    @Autowired
    BusRepository busRepository;

    @Autowired
    private TestRestTemplate rest;

    private List<Bus> buses = new ArrayList<>();


    @BeforeEach
    void init() {
        Bus b1 = busRepository.save(new Bus("XYZ-101","Mazda"));
        Bus b2 = busRepository.save(new Bus("XYZ-102","Nissan"));
        Bus b3 = busRepository.save(new Bus("XYZ-103","Toyota"));

        buses.add(b1);
        buses.add(b2);
        buses.add(b3);
    }

 
    @Test
    void getAllBuses() throws Exception{
        ResponseEntity<Bus[]> response = rest.getForEntity("http://localhost:" + port + "/api/bus/list", Bus[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(buses.toArray(), response.getBody());
    }

    @Test
    void findBusById() throws Exception{
        ResponseEntity<Bus> response = rest.getForEntity("http://localhost:" + port + "/api/bus/1", Bus.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new Bus("XYZ-101","Mazda"), response.getBody());
    }


    @Test
    void save() throws Exception {
        Bus bus = new Bus("XYZ-101","Mazda");
        ResponseEntity<Bus> response = rest.postForEntity("http://localhost:" + port + "/api/bus", 
        bus, Bus.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bus, response.getBody());
    }

    @Test
    void delete() throws Exception {
        Bus bus = busRepository.save(new Bus("XYZ-101","Mazda"));
        Long busId = bus.getId();
        rest.delete("http://localhost:" + port + "/api/bus/delete/"+busId);
        assertFalse(busRepository.findById(busId).isPresent());
    }
    
}
