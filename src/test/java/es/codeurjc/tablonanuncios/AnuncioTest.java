package es.codeurjc.tablonanuncios;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
		driver = new ChromeDriver();
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

}
