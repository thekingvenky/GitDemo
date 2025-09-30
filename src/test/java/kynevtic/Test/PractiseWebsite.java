package kynevtic.Test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import kynevtic.pageobjects.LandingPagePOM;

public class PractiseWebsite {
	
	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		//System.setProperty("selenium.webdriver.driver.", "/SeleniumFrameworkDesign/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		String url = "https://rahulshettyacademy.com/client/";
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		
		LandingPagePOM practisePagePom = new LandingPagePOM(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("kratos@godofwar.com");
		driver.findElement(By.id("userPassword")).sendKeys("@Ragnarok2022");
		driver.findElement(By.id("login")).click();
		
		List<WebElement> products = driver.findElements(By.xpath("//div[@class='card-body']"));
		
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div#toast-container"))));
		
		String[] productNames = {"IPHONE 13 PRO", "ZARA COAT 3", "ADIDAS ORIGINAL"};
		
		WebElement prod;
		
		for (String str : productNames) {
			prod = products.stream()
			.filter(p->p.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(str)).findFirst().orElse(null);
			
			if (prod == null)
				Assert.assertFalse(true);
			
			prod.findElement(By.xpath("//h5/b[text()='"+ str +"']/parent::h5/following-sibling::button[2]")).click();
					
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div#toast-container"))));
			WebElement toast = driver.findElement(By.cssSelector("div#toast-container>div>div"));
					
			Assert.assertEquals(toast.getText(), "Product Added To Cart");
			
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div#toast-container"))));
		}
		
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		List<String> cartItemNames = driver.findElements(By.cssSelector("div.cart h3")).stream().map(s->s.getText()).toList();
		
		for (String str : productNames) {
			Assert.assertTrue(cartItemNames.stream().anyMatch(c->c.equalsIgnoreCase(str)));
		}
		
		WebElement checkoutBtn = driver.findElement(By.xpath("//button[text()='Checkout']"));
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true)", checkoutBtn);
		
		Thread.sleep(1500);
		
		checkoutBtn.click();
		
		Select s = new Select(driver.findElement(By.xpath("//div[text()='Expiry Date ']/following-sibling::select[1]")));
		s.selectByVisibleText("04");
		
		s = new Select(driver.findElement(By.xpath("//div[text()='Expiry Date ']/following-sibling::select[2]")));
		s.selectByVisibleText("31");
		
		driver.findElement(By.xpath("//div[text()='CVV Code ']/following-sibling::input")).sendKeys("127");
		
		driver.findElement(By.xpath("//div[text()='Name on Card ']/following-sibling::input")).sendKeys("Kratos");
	
//		Actions a = new Actions(driver);
//		a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "Ind").build().perform();
		
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("Ind");
		
		WebElement country = driver.findElements(By.xpath("//button/span")).stream()
				.filter(c->c.getText().trim().equalsIgnoreCase("India")).findFirst().orElse(null);
		
		country.click();
		
		jse.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[text()='Place Order ']")));
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
		
		jse.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//h1")));
		Thread.sleep(1000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//h1")).getText().trim().equalsIgnoreCase("Thankyou for the order."));
		
		driver.quit();
	}
}
