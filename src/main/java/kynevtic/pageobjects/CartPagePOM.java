package kynevtic.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import kynevtic.AbstractComponents.AbstractComponent;

public class CartPagePOM extends AbstractComponent{
	
	WebDriver driver;
	
	public CartPagePOM(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
		
	@FindBy(xpath = "//button[text()='Checkout']")
	private WebElement checkoutButtonElement;
	
	private By cartItemsBy = By.cssSelector("div.cart h3");
	
	public List<String> getCartItems() {
		List<String> cartItemNames = driver.findElements(cartItemsBy).stream().map(s->s.getText()).toList();
		
		return cartItemNames;
	}
	
	public void goToCheckout() throws InterruptedException {
		scrollIntoViewJSE(checkoutButtonElement);
		Thread.sleep(1500);
		checkoutButtonElement.click();
	}
	
	public boolean verifyProductExistsInCart(String productName) {
		List<String> cartItemNames = driver.findElements(cartItemsBy).stream().map(s->s.getText()).toList();
		
		return cartItemNames.stream().anyMatch(s->s.equalsIgnoreCase(productName));
	}
}
