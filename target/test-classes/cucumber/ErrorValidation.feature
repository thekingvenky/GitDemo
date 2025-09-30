@tag
Feature: Error Validation
	
	@Error
	Scenario Outline: Negative Test of Login Functionality
		Given landed on Ecommerce Page
		When Logged in with username <username> and password <password>
		Then "Incorrect email or password." Error message is displayed

		
		Examples:
			| username 				| password		 |
			| leon@residentevil.com | @RE4Remake2023 |