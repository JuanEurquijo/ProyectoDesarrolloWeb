import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.web.project.ProyectoWebApplication;
import org.web.project.model.Driver;
import org.web.project.repository.DriverRepository;

@ActiveProfiles("systemtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = ProyectoWebApplication.class)
public class DriverSystemTest {

    private ChromeDriver browser;
    private WebDriverWait wait;

    @Autowired
    DriverRepository driverRepository;

    private String baseUrl;

    @BeforeEach
    void init() {
        driverRepository.save(new Driver("Pepe", "Perez", "1234567890", "11111111111", "Calle 170"));
        driverRepository.save(new Driver("Andres", "Perez", "0987654321", "2222222222", "Calle 180"));
        driverRepository.save(new Driver("Enrique", "Perez", "2523261212", "3333333333", "Calle 190"));

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

        this.wait = new WebDriverWait(browser, java.time.Duration.ofSeconds(50));

        this.baseUrl = "http://localhost:4200";

    }

    @AfterEach
    void end() {
        browser.quit();
    }

    @Test
    void listDrivers() {
        browser.get(baseUrl + "/driver/list");
       //WebElement btnDrivers = browser.findElement(By.id("linkdrivers"));
       //btnDrivers.click();
        WebElement user = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        user.sendKeys("coordinator");
        user.sendKeys(Keys.TAB);
        WebElement pass = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        pass.sendKeys("coordinator123");
        WebElement btnLogin = browser.findElement(By.id("kc-login"));
        btnLogin.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("drivers"), 3));
        List<WebElement> drivers = browser.findElements(By.className("drivers"));
        assertEquals("Pepe", drivers.get(0).findElement(By.className("table-text")).getText());
        assertEquals("Perez", drivers.get(0).findElements(By.className("table-text")).get(1).getText());
        assertEquals("1234567890", drivers.get(0).findElements(By.className("table-text")).get(2).getText());
        assertEquals("11111111111", drivers.get(0).findElements(By.className("table-text")).get(3).getText());
        assertEquals("Calle 170", drivers.get(0).findElements(By.className("table-text")).get(4).getText());
    }

    @Test
    void createDriver() {
        browser.get(baseUrl + "/driver/create");
        //WebElement btnDrivers = browser.findElement(By.id("linkdrivers"));
        //btnDrivers.click();
        //WebElement btnDriver = browser.findElement(By.id("btnNewDriver"));
        //btnDriver.click();
        WebElement nombre = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        nombre.sendKeys(Keys.TAB);
        nombre.sendKeys("Pablo");
        WebElement lastName = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lastName")));
        lastName.sendKeys(Keys.TAB);
        lastName.sendKeys("Alzate");
        WebElement identifier = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("identifier")));
        identifier.sendKeys(Keys.TAB);
        identifier.sendKeys("1111111111111");
        WebElement phone = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("phone")));
        phone.sendKeys("123456789010");
        WebElement address = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("address")));
        address.sendKeys("Colombia");
        WebElement btnSubmit = browser.findElement(By.id("btnSubmit"));
        btnSubmit.click();
        WebElement user = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        user.sendKeys("coordinator");
        user.sendKeys(Keys.TAB);
        WebElement pass = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        pass.sendKeys("coordinator123");
        WebElement btnLogin = browser.findElement(By.id("kc-login"));
        btnLogin.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("drivers"), 4));
        List<WebElement> drivers = browser.findElements(By.className("drivers"));
        assertEquals("Pablo", drivers.get(3).findElement(By.className("table-text")).getText());
        assertEquals("Alzate", drivers.get(3).findElements(By.className("table-text")).get(1).getText());
        assertEquals("1111111111111", drivers.get(3).findElements(By.className("table-text")).get(2).getText());
        assertEquals("123456789010", drivers.get(3).findElements(By.className("table-text")).get(3).getText());
        assertEquals("Colombia", drivers.get(3).findElements(By.className("table-text")).get(4).getText());
    }

}
