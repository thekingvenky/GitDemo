package kynevtic.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import kynevtic.TestComponents.BaseTest;
import kynevtic.pageobjects.CartPagePOM;
import kynevtic.pageobjects.CheckoutPagePOM;
import kynevtic.pageobjects.ProductsPagePOM;

public class SubmitOrderTest extends BaseTest{
	
	@Test(dataProvider = "getData", groups = "Purchase Product")
	public void submitOrderTest(HashMap<String, String> map) throws InterruptedException, IOException {
		
//		launchApplication();
		landingPage.loginApplication(map.get("email"), map.get("password"));
		
		ProductsPagePOM productsPage = new ProductsPagePOM(driver);
		productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
		
		String[] productNames = {"iphone 13 pro", "ZARA COAT 3", "ADIDAS ORIGINAL"};
		
		for (String str : productNames) {
			productsPage.addProductToCart(str);
			productsPage.waitForVisibilityOfElement(productsPage.toastElement);
			Assert.assertEquals(productsPage.toastMessageElement.getText(), "Product Added To Cart");
			productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
		}
		
		productsPage.clickCart();
		
		CartPagePOM cartPage = new CartPagePOM(driver);
		
		List<String> cartItems = cartPage.getCartItems();
				
		for (String itemName : productNames) {
			Assert.assertTrue(cartItems.stream().anyMatch(c->c.equalsIgnoreCase(itemName)));
		}
		
		cartPage.goToCheckout();
		
		CheckoutPagePOM checkoutPage = new CheckoutPagePOM(driver);
		checkoutPage.setCardExpiryMonth("04");
		checkoutPage.setCardExpiryYear("31");
		checkoutPage.enterCVVCode("127");
		checkoutPage.enterNameOnCard("Kratos");
		checkoutPage.selectCountry("India");
		
//		Actions a = new Actions(driver);
//		a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "Ind").build().perform();
		
		checkoutPage.placeOrder();
		
		String orderConfirmationText = checkoutPage.getOrderConfirmationText();
		
		Assert.assertTrue(orderConfirmationText.equalsIgnoreCase("Thankyou for the order."));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "kratos@godofwar.com");
//		map1.put("password", "@Ragnarok2022");
//		
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		map2.put("email", "leon@residentevil.com");
//		map2.put("password", "@RE4Remake2023");
		
		List<HashMap<String, String>> data = getJsonToHash(System.getProperty("user.dir") + "//src//test//java//kynevtic//data//SubmitOrder.json");
		
		return new Object [] [] {{data.get(0)}, {data.get(1)}};
	}
	
//	@DataProvider
//	public Object[][] getData() {		
//		return new Object [] [] {{"kratos@godofwar.com", "@Ragnarok2022"}, {"leon@residentevil.com", "@RE4Remake2023"}};
//	}
	
}
