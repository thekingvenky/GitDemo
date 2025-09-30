package kynevtic.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import kynevtic.AbstractComponents.AbstractComponent;

public class LandingPagePOM extends AbstractComponent{

	WebDriver driver;
	
	public LandingPagePOM(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	private WebElement userEmail;
	
	@FindBy(id="userPassword")
	private WebElement userPassword;
	
	@FindBy(id="login")
	private WebElement login;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/cart']")
	private WebElement cart;
	
	@FindBy(css = "div.toast-message")
	private WebElement loginToastElement;
	
	public void loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		login.click();
	}
	
	public void goTo(String urlString) {
		driver.get(urlString);
	}
	
	public String failedLogin() {
		waitForVisibilityOfElement(loginToastElement);
		return loginToastElement.getText();
	}
}	

