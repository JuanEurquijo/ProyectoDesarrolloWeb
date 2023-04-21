import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.web.project.model.Station;
import org.web.project.repository.StationRepository;


@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProyectoWebApplication.class)
public class StationControllerIntegrationTest {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

    @LocalServerPort
    private int port;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    private TestRestTemplate rest;

    private List<Station> stations = new ArrayList<>();


    @BeforeEach
    void init() {
        Station s1 = stationRepository.save(new Station("Calle 39"));
        Station s2 = stationRepository.save(new Station("Calle 45"));
        Station s3 = stationRepository.save(new Station("Marly"));

        stations.add(s1);
        stations.add(s2);
        stations.add(s3);
    }

 
    @Test
    void getAllStations() throws Exception{
        ResponseEntity<Station[]> response = rest.getForEntity("http://localhost:" + port + "/api/station/list", Station[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(stations.toArray(), response.getBody());
    }

    @Test
    void findStationById() throws Exception{
        ResponseEntity<Station> response = rest.getForEntity("http://localhost:" + port + "/api/station/1", Station.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new Station("Calle 39"), response.getBody());
    }


    
    
}
