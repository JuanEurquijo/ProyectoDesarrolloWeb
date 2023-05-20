import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.web.project.ProyectoWebApplication;
import org.web.project.model.Bus;
import org.web.project.model.Driver;
import org.web.project.model.Route;
import org.web.project.model.Station;
import org.web.project.repository.AsociationRepository;
import org.web.project.repository.BusRepository;
import org.web.project.repository.DriverRepository;
import org.web.project.repository.RouteRepository;
import org.web.project.repository.ScheduleRepository;
import org.web.project.repository.StationRepository;

@ActiveProfiles("systemtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = ProyectoWebApplication.class)
public class AsociationSystemTest {
    private ChromeDriver browser;
    private WebDriverWait wait;

    @Autowired
    AsociationRepository asociationRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    BusRepository busRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    StationRepository stationRepository;

    private String baseUrl;

    private List<Station> stations = new ArrayList<>();

    @BeforeEach
    void init() {
        driverRepository.save(new Driver("Pepe", "Perez", "1234567890", "11111111111", "Calle 170"));
        busRepository.save(new Bus("XYZ-101", "Mazda"));
        Station s1 = stationRepository.save(new Station("Calle 45"));
        Station s2 = stationRepository.save(new Station("Calle 100"));
        stations.add(s1);
        stations.add(s2);

        routeRepository.save(new Route("A-10", stations));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        // options.addArguments("--headless"); // To hide Chrome window
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-extensions"); // disabling extensions
        // options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--force-device-scale-factor=0.8");
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        this.browser = new ChromeDriver(options);

        this.wait = new WebDriverWait(browser, java.time.Duration.ofSeconds(10));

        this.baseUrl = "http://localhost:4200";

    }

    @AfterEach
    void end() {
       browser.quit();
    }

    @Test
    void createAssignment() {
        browser.get(baseUrl + "/route/list");
         WebElement user = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
         user.sendKeys("administrator");
         user.sendKeys(Keys.TAB);
         WebElement pass = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
         pass.sendKeys("admin123");
         WebElement btnLogin = browser.findElement(By.id("kc-login"));
         btnLogin.click();
         wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // Verificar si la información está presente en la base de datos
                return driverRepository.count() > 0 && busRepository.count() > 0 && stationRepository.count() > 0;
            }
        });
         wait.until(ExpectedConditions.numberOfElementsToBe(By.className("routes"), 1));
         WebElement btnAssign = browser.findElement(By.id("assignId"));
         btnAssign.click();
        selectOptionFromDropdown(browser, "selectedDriver", "Pepe");
        selectOptionFromDropdown(browser, "selectedBus", "XYZ-101");
        selectOptionFromDropdown(browser, "selectedRoute", "A-10");
        selectOptionFromDropdown(browser, "selectedDay", "Lunes");
        WebElement startTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("startTime")));
        startTime.click();
        By arrowUpLocator = By.xpath("//span[contains(@class, 'pi pi-chevron-up ng-tns-c15-0')]");
        wait.until(ExpectedConditions.elementToBeClickable(arrowUpLocator)).click();
        WebElement endTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("endTime")));
        endTime.click();
        By arrowDownLocator = By.xpath("//span[contains(@class, 'pi pi-chevron-down ng-tns-c15-1')]");
        wait.until(ExpectedConditions.elementToBeClickable(arrowDownLocator)).click();
        WebElement btnSave = browser.findElement(By.id("btnAssignment"));
        btnSave.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("assignments"), 1));


    }

    public void selectOptionFromDropdown(WebDriver browser, String dropdownId, String optionText) {
        WebElement dropdown = browser.findElement(By.id(dropdownId));
        
        Actions actions = new Actions(browser);
        actions.moveToElement(dropdown).click().perform();
        
        By optionLocator = By.xpath("//li[contains(@class, 'p-dropdown-item')]");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionLocator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
        List<WebElement> options = browser.findElements(optionLocator);
        
        for (WebElement option : options) {
            if (option.getText().equals(optionText)) {
                actions.moveToElement(option).click().perform();
                break;
            }
        }
    }
    

}
