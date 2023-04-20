package org.web.project.init;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.web.project.model.Bus;
import org.web.project.model.Driver;
import org.web.project.model.Route;
import org.web.project.model.Schedule;
import org.web.project.model.Station;
import org.web.project.repository.BusRepository;
import org.web.project.repository.DriverRepository;
import org.web.project.repository.RouteRepository;
import org.web.project.repository.ScheduleRepository;
import org.web.project.repository.StationRepository;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StationRepository stationRepository;
    

    @Override
    @Transactional
    public void run(String... args) throws Exception {
       Random random = new Random(123);
       List<Station> stations = new ArrayList<>();
       Station station = new Station("Calle 100");
       stationRepository.save(station);
       stations.add(station);

       RandomStringGenerator randomGen = new RandomStringGenerator.Builder().withinRange('a','z')
       .usingRandom(random::nextInt).build();
       

       for(int i=0; i < 30; i++){
         String name = randomGen.generate(5,8);
         String lastName = randomGen.generate(5,8);
         String dir = randomGen.generate(5,8);
         Long ident = Math.abs(random.nextLong());
         Long phone = Math.abs(random.nextLong());
         driverRepository.save(new Driver(name, lastName, String.valueOf(ident), String.valueOf(phone), dir));
       }

       for(int i=0; i < 30; i++){
        String string = randomGen.generate(5,8);
        String plate = "XYZ-10"+i;
        busRepository.save(new Bus(plate, string));
      }

      for(int i=0; i < 5; i++){
        String string = randomGen.generate(5,8);
        stationRepository.save(new Station(string));
      }

      for(int i=0; i < 10; i++){
        String plate = "A-1"+i;
        routeRepository.save(new Route(plate,stations));
      }

      for(int i=0; i < 5; i++){
        String string = randomGen.generate(5,8);
        Random rand = new Random();
        Calendar calendar = new GregorianCalendar();
        int year = rand.nextInt(100) + 1900; 
        int month = rand.nextInt(12) + 1; 
        int day = rand.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1; 
        int hour = rand.nextInt(24); 
        int minute = rand.nextInt(60); 
        int second = rand.nextInt(60); 
        calendar.set(year, month, day, hour, minute, second);
        scheduleRepository.save(new Schedule(string,calendar.getTime(),calendar.getTime()));
      }

    
    }

}
