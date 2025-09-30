package kynevtic.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import kynevtic.AbstractComponents.AbstractComponent;

public class ProductsPagePOM extends AbstractComponent{

	WebDriver driver;
	
	public ProductsPagePOM(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
		
	@FindBy(xpath = "//div[@class='card-body']")
	private List<WebElement> productsElements;
	
	@FindBy(css = "div#toast-container")
	public WebElement toastElement;
	
	@FindBy(css = "div#toast-container>div>div")
	public WebElement toastMessageElement;
	
	private By productTitleBy = By.cssSelector("b");
	
	public WebElement getProductName(String productName) {
		WebElement prod = productsElements.stream()
						.filter(p->p.findElement(productTitleBy).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
				
		if (prod == null)
			Assert.assertFalse(true);
				
		return prod;
	}
	
	public void addProductToCart(String productName) {
		WebElement product = getProductName(productName);
		product.findElement(By.xpath("//h5/b[text()='"+ productName +"']/parent::h5/following-sibling::button[2]")).click();
	}
	
	public boolean verifyProductExists(String productName)
	{
		WebElement prod = productsElements.stream().filter(p->p.findElement(productTitleBy).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		
		if (prod == null) {
			return false;
		}
		else {
			return true;
		}
	}
}	

