package MakePlannieWork.Plannie.views;
import MakePlannieWork.Plannie.TestsHelper;
import MakePlannieWork.Plannie.model.Gebruiker;
import MakePlannieWork.Plannie.model.Groep;
import MakePlannieWork.Plannie.repository.GebruikerRepository;
import MakePlannieWork.Plannie.repository.GroepRepository;
import MakePlannieWork.Plannie.service.PlannieGroepService;
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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GroepViewTests {

    private TestsHelper testsHelper;
    private WebDriver driver;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Autowired
    private GroepRepository groepRepository;

    @Autowired
    private PlannieGroepService plannieGroepService;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "Algemeen/chromedriver.exe");
        this.driver = new ChromeDriver();
        this.testsHelper = new TestsHelper(this.driver, this.gebruikerRepository, this.passwordEncoder,
                this.groepRepository);
    }

    @After
    public void tearDown() {

        this.testsHelper.clear();
        this.driver.quit();
        this.driver = null;
        this.testsHelper = null;
    }

    @Test
    public void testGroepAanmakenCorrect() throws InterruptedException {
        // Arrange
        this.driver.get("http://localhost:8080/gebruikerDetail");
        this.testsHelper.maakTestGebruiker();
        this.testsHelper.maakTestGroep();
        this.testsHelper.registreerTestGebruikers();
        this.testsHelper.inloggen();
        Gebruiker testGebruiker = this.testsHelper.geefTestGebruiker();
        Groep testGroep = this.testsHelper.geefTestGroep();
        this.testsHelper.wachtOpTitel("Welkom bij Plannie - " + testGebruiker.getVoornaam());

        // Activate
        driver.findElement(By.name("groepsNaam")).sendKeys(testGroep.getGroepsNaam());
        driver.findElement(By.id("groepAanmaken")).click();
        this.testsHelper.wachtOpTitel("Plannie - Groepsdetails " + testGroep.getGroepsNaam());

        // Assert
        assertEquals("Plannie - Groepsdetails " + testGroep.getGroepsNaam(), driver.getTitle());
    }

    @Test
    public void testGroepAanmakenFout() throws InterruptedException {
        // Arrange
        this.driver.get("http://localhost:8080/gebruikerDetail");
        this.testsHelper.maakTestGebruiker();
        this.testsHelper.registreerTestGebruikers();
        this.testsHelper.inloggen();
        Gebruiker testGebruiker = this.testsHelper.geefTestGebruiker();
        String testGroepsNaam = "";
        this.testsHelper.wachtOpTitel("Welkom bij Plannie - " + testGebruiker.getVoornaam());

        // Activate
        driver.findElement(By.name("groepsNaam")).sendKeys(testGroepsNaam);
        driver.findElement(By.id("groepAanmaken")).click();
        Thread.sleep(500);

        // Assert
        assertEquals("Welkom bij Plannie - " + testGebruiker.getVoornaam(), driver.getTitle());
    }

    @Test
    public void testGroepNaamWijzigenCorrect() throws InterruptedException {
        // Arrange
        this.driver.get("http://localhost:8080/gebruikerDetail");
        this.testsHelper.zetTestGebruikerEnTestGroepKlaar();
        Groep testGroep = this.testsHelper.geefTestGroep();
        this.driver.get("http://localhost:8080/groepDetail/" + testGroep.getGroepId());
        String testGroepNaam = testGroep.getGroepsNaam() + "Testing";

        // Activate
        driver.findElement(By.id("wijzigGroepsNaam2")).click();
        this.testsHelper.wachtOpElement("groepsNaamWijzigingsFormulier");
        driver.findElement(By.name("groepsNaam")).clear();
        driver.findElement(By.name("groepsNaam")).sendKeys(testGroepNaam);
        driver.findElement(By.id("groepsNaamWijzigen")).click();
        this.testsHelper.wachtOpTitel("Plannie - Groepsdetails " + testGroepNaam);

        // Assert
        assertEquals("Plannie - Groepsdetails " + testGroepNaam, driver.getTitle());
    }

    @Test
    public void testGroepNaamWijzigenFout() throws InterruptedException {
        // Arrange
        this.driver.get("http://localhost:8080/gebruikerDetail");
        this.testsHelper.zetTestGebruikerEnTestGroepKlaar();
        Groep testGroep = this.testsHelper.geefTestGroep();
        this.driver.get("http://localhost:8080/groepDetail/" + testGroep.getGroepId());
        String testGroepNaam = "";

        // Activate
        driver.findElement(By.id("wijzigGroepsNaam2")).click();
        this.testsHelper.wachtOpElement("groepsNaamWijzigingsFormulier");
        driver.findElement(By.name("groepsNaam")).clear();
        driver.findElement(By.name("groepsNaam")).sendKeys(testGroepNaam);
        driver.findElement(By.id("groepsNaamWijzigen")).click();
        Thread.sleep(500);

        // Assert
        assertEquals("Plannie - Groepsdetails " + testGroep.getGroepsNaam(), driver.getTitle());
    }

    @Test
    public void testGroepsLedenToevoegenEnVerwijderen() throws InterruptedException {
        // Arrange. 3 tests gebruikers, waarvan gebruiker 1 ingelogd wordt en een groep aanmaakt.
        this.driver.get("http://localhost:8080/gebruikerDetail");
        this.testsHelper.maakTestGebruikers(3);
        this.testsHelper.registreerTestGebruikers();
        this.testsHelper.inloggen();
        this.testsHelper.maakTestGroep();
        this.testsHelper.registreerTestGroepen();
        Groep testGroep = this.testsHelper.geefTestGroep();
        ArrayList<Gebruiker> testGebruikers = this.testsHelper.geefTestGebruikers();
        this.driver.get("http://localhost:8080/groepDetail/" + testGroep.getGroepId());
        boolean testGebruikerToegevoegd = false;
        boolean testGebruikerVerwijdert = false;

        // Activate, de andere testgebruikers worden toegevoegd aan de groep, en eentje wordt weer verwijdert.
        this.driver.findElement(By.id("voeg" + testGebruikers.get(1).getVoornaam() + "ToeAanGroep")).click();
        Thread.sleep(500);
        this.driver.findElement(By.id("voeg" + testGebruikers.get(2).getVoornaam() + "ToeAanGroep")).click();
        Thread.sleep(500);
        this.driver.findElement(By.id("Verwijder" + testGebruikers.get(2).getVoornaam() + "UitGroep")).click();
        Thread.sleep(500);

        // Assert
        if (this.driver.findElement(By.id("Verwijder" + testGebruikers.get(1).getVoornaam() + "UitGroep")).getSize().width != 0) {
            testGebruikerToegevoegd = true;
        }
        if (this.driver.findElement(By.id("voeg" + testGebruikers.get(2).getVoornaam() + "ToeAanGroep")).getSize().width != 0) {
            testGebruikerVerwijdert = true;
        }
        assertTrue(testGebruikerToegevoegd);
        assertTrue(testGebruikerVerwijdert);
    }
}
