package es.codeurjc.tablonanuncios;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import es.codeurjc.test.tablonanuncios.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnuncioTest {

	@LocalServerPort
    int port;

	WebDriver driver;
	
	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--headless");
		options.setCapability(ChromeOptions.CAPABILITY, silentCapability());
		driver = new ChromeDriver(options);
	}
	
	@AfterEach
	public void teardown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void createAnuncio() throws InterruptedException {
		driver.get("http://localhost:"+this.port+"/");
		
		driver.findElement(By.linkText("Nuevo anuncio")).click();
		
		driver.findElement(By.name("nombre")).sendKeys("Michel");
		driver.findElement(By.name("asunto")).sendKeys("Vendo moto roja");
		driver.findElement(By.name("comentario")).sendKeys("Un comentario muy largo...");
		
		driver.findElement(By.id("enviar")).click();
		
		driver.findElement(By.linkText("Volver al tablón")).click();
		
		assertNotNull(driver.findElement(By.partialLinkText("roja")));
	}

	private DesiredCapabilities silentCapability(){
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		logPrefs.enable(LogType.PROFILER, Level.INFO);
		logPrefs.enable(LogType.BROWSER, Level.INFO);
		logPrefs.enable(LogType.CLIENT, Level.INFO);
		logPrefs.enable(LogType.DRIVER, Level.INFO);
		logPrefs.enable(LogType.SERVER, Level.INFO);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		return caps;
	}

}
