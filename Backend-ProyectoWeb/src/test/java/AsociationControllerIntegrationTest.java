import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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
import org.web.project.model.Asociation;
import org.web.project.model.Bus;
import org.web.project.model.Driver;
import org.web.project.model.Route;
import org.web.project.model.Schedule;
import org.web.project.model.Station;
import org.web.project.repository.AsociationRepository;
import org.web.project.repository.BusRepository;
import org.web.project.repository.DriverRepository;
import org.web.project.repository.RouteRepository;
import org.web.project.repository.ScheduleRepository;
import org.web.project.repository.StationRepository;


@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProyectoWebApplication.class)
public class AsociationControllerIntegrationTest {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private AsociationRepository asociationRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TestRestTemplate rest;

    ArrayList<Asociation> asociations = new ArrayList<Asociation>();

    List<Station> stations = new ArrayList<Station>();

    @BeforeEach
    void init() {
        Station s1 = stationRepository.save(new Station("Calle 45"));
        Station s2 = stationRepository.save(new Station("Calle 100"));
        stations.add(s1);
        stations.add(s2);

        Driver driver = driverRepository.save(new Driver("Andres", "Perez", "1234567890", "1111111111", "Calle 170"));
        Bus bus = busRepository.save(new Bus("XYZ-123", "Mazda"));
        Schedule schedule = scheduleRepository.save(new Schedule("Lunes",new GregorianCalendar(1, 1, 1, 1, 1, 1).getTime(), new GregorianCalendar(2, 2, 2, 2, 2, 2).getTime()));
        Route route = routeRepository.save(new Route("A-10", stations));
        asociationRepository.save(new Asociation(driver, bus, route, schedule));
        asociations.add(new Asociation(driver, bus, route, schedule));

    }


    @Test
    void findSchedulesByRoute() throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = scheduleRepository.save(new Schedule("Lunes", new GregorianCalendar(1, 1, 1, 1, 1, 1).getTime(),
        new GregorianCalendar(2, 2, 2, 2, 2, 2).getTime()));
        schedules.add(schedule);
        ResponseEntity<Schedule[]> response = rest
                .getForEntity("http://localhost:" + port + "/api/assignment/1/schedules", Schedule[].class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(schedules.toArray(), response.getBody());
    }

}
