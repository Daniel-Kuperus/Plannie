package MakePlannieWork.Plannie.views;

import MakePlannieWork.Plannie.TestsHelper;
import MakePlannieWork.Plannie.model.Gebruiker;
import MakePlannieWork.Plannie.repository.GebruikerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ErrorViewTests {

    final String nietBestaandeURL = "http://localhost:8080/nietbestaandeurl";

    private TestsHelper testsHelper;
    private WebDriver driver;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "Algemeen/chromedriver.exe");
        this.driver = new ChromeDriver();
        this.testsHelper = new TestsHelper(this.driver, this.gebruikerRepository, this.passwordEncoder);
    }

    @After
    public void tearDown() {

        this.testsHelper.verwijderTestGebruikersUitDatabase();
        this.driver.quit();
        this.driver = null;
        this.testsHelper = null;
    }

    @Test
    public void testError() throws InterruptedException {
        // Arrange
        this.driver.get("http://localhost:8080/gebruikerDetail");
        this.testsHelper.maakTestGebruiker();
        this.testsHelper.registreerTestGebruikers();
        this.testsHelper.inloggen();

        // Activate
        this.driver.get(nietBestaandeURL);
        this.testsHelper.wachtOpTitel("Error!!!");

        // Assert
        assertEquals("Error!!!",driver.getTitle());
    }
}
