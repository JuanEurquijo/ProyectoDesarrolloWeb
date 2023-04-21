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
import org.web.project.model.Route;
import org.web.project.model.Station;
import org.web.project.repository.RouteRepository;
import org.web.project.repository.StationRepository;

@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProyectoWebApplication.class)
public class RouteControllerIntegrationTest {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

    @LocalServerPort
    private int port;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    private TestRestTemplate rest;

    private List<Route> routes = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();


    @BeforeEach
    void init() {


        Station s1 = stationRepository.save(new Station("Calle 45"));
        Station s2 = stationRepository.save(new Station("Calle 100"));
        stations.add(s1);
        stations.add(s2);

        routeRepository.save(new Route("A-10",stations));
        routeRepository.save(new Route("A-11",stations));
        routeRepository.save(new Route("A-12",stations));

        routes.add(new Route("A-10",stations));
        routes.add(new Route("A-11",stations));
        routes.add(new Route("A-12",stations));
    }

 
    @Test
    void getAllRoutes() throws Exception{
        ResponseEntity<Route[]> response = rest.getForEntity("http://localhost:" + port + "/api/route/list", Route[].class);
        System.out.println("HOLAAA" + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(routes.toArray(), response.getBody());
    }

    @Test
    void findRouteById() throws Exception{
        ResponseEntity<Route> response = rest.getForEntity("http://localhost:" + port + "/api/route/1", Route.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new Route("A-10",stations), response.getBody());
    }


    @Test
    void save() throws Exception {
        Route route = new Route("A-10",stations);
        ResponseEntity<Route> response = rest.postForEntity("http://localhost:" + port + "/api/route", 
        route, Route.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(route, response.getBody());
    }

    @Test
    void delete() throws Exception {
        Route route = routeRepository.save(new Route("A-10",stations));
        Long routeId = route.getId();
        rest.delete("http://localhost:" + port + "/api/route/delete/"+routeId);
        assertFalse(routeRepository.findById(routeId).isPresent());
    }
    
}
