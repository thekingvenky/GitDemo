@tag
Feature: Purchase the order from Ecommerce website

	Background:
	Given landed on Ecommerce Page
	
	@Regression
	Scenario Outline: Positive Test of Submitting the order
		Given Logged in with username <username> and password <password>
		When I add the product <productName> to the cart
		And Checkout <productName> and submit the order
		Then "THANKYOU FOR THE ORDER." message is displayed on the Confirmation Page

		
		Examples:
			| username 				| password		 | productName	 |
			| kratos@godofwar.com 	| @Ragnarok2022	 | ZARA COAT 3	 |