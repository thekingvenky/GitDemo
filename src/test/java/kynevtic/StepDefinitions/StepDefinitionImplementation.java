package kynevtic.StepDefinitions;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kynevtic.TestComponents.BaseTest;
import kynevtic.pageobjects.CartPagePOM;
import kynevtic.pageobjects.CheckoutPagePOM;
import kynevtic.pageobjects.LandingPagePOM;
import kynevtic.pageobjects.ProductsPagePOM;

public class StepDefinitionImplementation extends BaseTest {
	
	LandingPagePOM landingPage;
	ProductsPagePOM productsPage;
	CartPagePOM cartPage;
	CheckoutPagePOM checkoutPage;
	
	@Given("landed on Ecommerce Page")
	public void landed_on_ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_password(String username, String password) {
		landingPage.loginApplication(username, password);
	}
	
	@When("^I add the product (.+) to the cart$")
	public void add_products_to_cart(String productName) throws InterruptedException {
		productsPage = new ProductsPagePOM(driver);
		productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
		
		String[] productNames = {"iphone 13 pro", "ZARA COAT 3", "ADIDAS ORIGINAL"};
		
		
		productsPage.addProductToCart(productName);
		productsPage.waitForVisibilityOfElement(productsPage.toastElement);
		Assert.assertEquals(productsPage.toastMessageElement.getText(), "Product Added To Cart");
		productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_order(String productName) throws InterruptedException {
		productsPage.clickCart();
		
		cartPage = new CartPagePOM(driver);
		
		List<String> cartItems = cartPage.getCartItems();
				
		Assert.assertTrue(cartItems.stream().anyMatch(c->c.equalsIgnoreCase(productName)));
		
		cartPage.goToCheckout();
		
		checkoutPage = new CheckoutPagePOM(driver);
		checkoutPage.setCardExpiryMonth("04");
		checkoutPage.setCardExpiryYear("31");
		checkoutPage.enterCVVCode("127");
		checkoutPage.enterNameOnCard("Kratos");
		checkoutPage.selectCountry("India");
		
		checkoutPage.placeOrder();
	}
	
	@Then("{string} message is displayed on the Confirmation Page")
	public void message_displayed_confirmation_page(String confirmatString) throws InterruptedException {
		String orderConfirmationText = checkoutPage.getOrderConfirmationText();
		
		Assert.assertTrue(orderConfirmationText.equalsIgnoreCase(confirmatString));
		driver.quit();
	}
	
	@Then("{string} Error message is displayed")
	public void error_message_displayed(String errorMsg) {
		String errorString = landingPage.failedLogin();
		Assert.assertEquals(errorString, errorMsg);
		driver.quit();
	}
}
