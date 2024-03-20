package pk_spreecom;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Create_Address_And_Update_Address_Using_File_with_Util_Function {

	String ID;

	@Test(priority = 1)
	public void Create_Address() throws IOException, ParseException {

		Response response = RestAssured.given()
				.auth()
				.oauth2(util_function.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(util_function.readFile("address.json"))
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		// System.out.println("Response Body is => " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Map<String, String> id_create = response.jsonPath().getJsonObject("data");
		ID = id_create.get("id");

	}

	@Test(priority = 2)
	public void Update_Address() throws IOException, ParseException {

		Response response = RestAssured.given()
				.auth()
				.oauth2(util_function.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(util_function.readFile("updateAddress.json"))
				.patch("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + ID)
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		// System.out.println("Response Body is => " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Map<String, String> default_billing_address = response.jsonPath().getJsonObject("data.attributes");
		String firstName = default_billing_address.get("firstname");
		Assert.assertEquals(firstName, "Shiva");

		String lastName = default_billing_address.get("lastname");
		Assert.assertEquals(lastName, "Sandhi");

		String addressOne = default_billing_address.get("address1");
		Assert.assertEquals(addressOne, "RKH colony");

		String addressTwo = default_billing_address.get("address2");
		Assert.assertEquals(addressTwo, "A S Rao nagar");
	}
	
	@Test(dependsOnMethods = {"Create_Address"})
	public void deleteAddress()
	{
		Response response = RestAssured.given()
				.auth()
				.oauth2(util_function.oAuth_Token())
				.delete("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/"+ID)
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();
		
		 // Status Code Validation
		  int statusCode = response.getStatusCode();
		  System.out.println("Status code is =>  " + statusCode);
		  Assert.assertEquals(301, statusCode);	
	}


}
