package kynevtic.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import kynevtic.AbstractComponents.AbstractComponent;

public class CheckoutPagePOM extends AbstractComponent{
	WebDriver driver;
	
	public CheckoutPagePOM(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[text()='Expiry Date ']/following-sibling::select[1]")
	private WebElement cardExpiryMonthDropDownElement;
	
	@FindBy(xpath = "//div[text()='Expiry Date ']/following-sibling::select[2]")
	private WebElement cardExpiryYearDropDownElement;
	
	@FindBy(xpath = "//div[text()='CVV Code ']/following-sibling::input")
	private WebElement cvvCodeElement;
	
	@FindBy(xpath = "//div[text()='Name on Card ']/following-sibling::input")
	private WebElement nameOnCardElement;
	
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	private WebElement selectCountryElement;
	
	@FindBy(xpath = "//button/span")
	private List<WebElement> countryNamesElements;
	
	@FindBy(xpath = "//a[text()='Place Order ']")
	private WebElement placeOrderElement;
	
	@FindBy(xpath = "//h1")
	private WebElement orderConfirmationTextElement;
	
	public void setCardExpiryMonth(String month) {
		Select s = new Select(cardExpiryMonthDropDownElement);
		s.selectByVisibleText(month);
	}
	
	public void setCardExpiryYear(String year) {
		Select s = new Select(cardExpiryYearDropDownElement);
		s.selectByVisibleText(year);
	}
	
	public void enterCVVCode(String cvv) {
		cvvCodeElement.sendKeys(cvv);
	}
	
	public void enterNameOnCard(String name) {
		nameOnCardElement.sendKeys(name);
	}
	
	public void selectCountry(String countryName) {
		selectCountryElement.sendKeys(countryName);
		
		WebElement country = countryNamesElements.stream()
				.filter(c->c.getText().trim().equalsIgnoreCase(countryName)).findFirst().orElse(null);
		
		country.click();
	}
	
	public void placeOrder() throws InterruptedException {
		scrollIntoViewJSE(placeOrderElement);
		Thread.sleep(1000);
		placeOrderElement.click();
	}
	
	public String getOrderConfirmationText() throws InterruptedException {
		scrollIntoViewJSE(orderConfirmationTextElement);
		Thread.sleep(1000);
		return orderConfirmationTextElement.getText().trim();
	}
}
