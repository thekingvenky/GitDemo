package kynevtic.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.sql.Driver;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import kynevtic.pageobjects.LandingPagePOM;

public class BaseTest {
	
	public WebDriver driver;
	public Properties property;
	public LandingPagePOM landingPage;
	
	public WebDriver initializeDriver() throws IOException {
		
		property = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\\\main\\\\java\\\\kynevtic\\\\resources\\\\global.properties");
		property.load(fis);
		
		String browser = System.getProperty("browser") != null ? System.getProperty("browser") : property.getProperty("browser");		 
				
//		String browser = property.getProperty("browser");
		
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			//System.setProperty("selenium.webdriver.driver.", "/SeleniumFrameworkDesign/resources/chromedriver.exe");
			driver = new ChromeDriver();
		} 
		
		else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			//System.setProperty("selenium.webdriver.driver.", "/SeleniumFrameworkDesign/resources/edgedriver.exe");
			driver = new EdgeDriver();
		} 
		
		else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//System.setProperty("selenium.webdriver.driver.", "/SeleniumFrameworkDesign/resources/firefoxdriver.exe");
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		return driver;
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPagePOM launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPagePOM(driver);
		landingPage.goTo(property.getProperty("url"));
		return landingPage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void close() {
		driver.quit();
	}
	
	public String getScreenshot(String testCaseName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "//images//screenshots//" + testCaseName + ".png");
		FileUtils.copyFile(src, dest);
		return System.getProperty("user.dir") + "//images//screenshots//" + testCaseName + ".png";
	}
	
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "//images//screenshots//" + testCaseName + ".png");
		FileUtils.copyFile(src, dest);
		return System.getProperty("user.dir") + "//images//screenshots//" + testCaseName + ".png";
	}
	
	public List<HashMap<String, String>> getJsonToHash(String filePath) throws IOException {
		//Reading JSON and convert it to String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		//Convert String to HashMap
		ObjectMapper mapper = new ObjectMapper();
		
		List<HashMap<String, String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		
		return data;
	}
}
