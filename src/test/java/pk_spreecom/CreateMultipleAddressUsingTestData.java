package pk_spreecom;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateMultipleAddressUsingTestData {
	
	
	@Test(dataProvider = "Addresses", dataProviderClass = spreecomTestData.class, priority = 1)
	public void addAddress(String fName, String lName, String address1,
			String city, String zipcode, String phone, String state, String country) {
		JSONObject newAddress = new JSONObject();
		newAddress.put("firstname", fName);
		newAddress.put("lastname", lName);
		newAddress.put("address1", address1);
		newAddress.put("city", city);
		newAddress.put("zipcode", zipcode);
		newAddress.put("phone", phone);
		newAddress.put("state_name", state);
		newAddress.put("country_iso", country);
		JSONObject body = new JSONObject();
		body.put("address", newAddress);
		Response response = RestAssured.given()
				.auth()
				.oauth2(util_function.oAuth_Token())
				.body(body)
				.contentType(ContentType.JSON)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		 int statusCode = response.getStatusCode();
		 Assert.assertEquals(200, statusCode);
		 
		 Map<String, String> default_billing_address = response.jsonPath().getJsonObject("data.attributes");
			String firstName = default_billing_address.get("firstname");
			Assert.assertEquals(firstName, fName);
	}

}
