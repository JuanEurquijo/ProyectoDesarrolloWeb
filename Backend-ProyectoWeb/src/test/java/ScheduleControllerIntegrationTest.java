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
import org.web.project.model.Schedule;
import org.web.project.repository.ScheduleRepository;

@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProyectoWebApplication.class)
public class ScheduleControllerIntegrationTest {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

    @LocalServerPort
    private int port;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    private TestRestTemplate rest;

    private List<Schedule> schedules = new ArrayList<>();


    @BeforeEach
    void init() {
        Schedule s1 = scheduleRepository.save(new Schedule("Lunes",new GregorianCalendar(1,1,1,1,1,1).getTime(),new GregorianCalendar(2,2,2,2,2,2).getTime()));
        Schedule s2 = scheduleRepository.save(new Schedule("Martes",new GregorianCalendar(1,1,1,1,1,1).getTime(),new GregorianCalendar(2,2,2,2,2,2).getTime()));
        Schedule s3 = scheduleRepository.save(new Schedule("Miercoles",new GregorianCalendar(1,1,1,1,1,1).getTime(),new GregorianCalendar(2,2,2,2,2,2).getTime()));

        schedules.add(s1);
        schedules.add(s2);
        schedules.add(s3);
    }
 
    @Test
    void getAllSchedules() throws Exception{
        ResponseEntity<Schedule[]> response = rest.getForEntity("http://localhost:" + port + "/api/schedule/list", Schedule[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(schedules.toArray(), response.getBody());
    }

    @Test
    void findScheduleById() throws Exception{
        ResponseEntity<Schedule> response = rest.getForEntity("http://localhost:" + port + "/api/schedule/1", Schedule.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new Schedule("Lunes",new GregorianCalendar(1,1,1,1,1,1).getTime(),new GregorianCalendar(2,2,2,2,2,2).getTime()), response.getBody());
    }


    @Test
    void save() throws Exception {
        Schedule schedule = new Schedule("Lunes",new GregorianCalendar(1,1,1,1,1,1).getTime(),new GregorianCalendar(2,2,2,2,2,2).getTime());
        ResponseEntity<Schedule> response = rest.postForEntity("http://localhost:" + port + "/api/schedule", 
        schedule, Schedule.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schedule, response.getBody());
    }

  
    
}
