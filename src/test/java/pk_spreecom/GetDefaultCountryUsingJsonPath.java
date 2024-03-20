package pk_spreecom;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetDefaultCountryUsingJsonPath {
	@Test
	  public void getDefaultCountry() 
	  {
		  RestAssured.baseURI = "https://demo.spreecommerce.org";
		  RequestSpecification httpRequest = RestAssured.given();
		  Response response = httpRequest.get("/api/v2/storefront/countries/Ind");
//		  Response response = httpRequest.request(Method.GET,"/api/v2/storefront/countries/Ind");
		  
			// Now let us print the body of the message to see what response
		  // we have recieved from the server
		  String responseBody = response.getBody().asString();
//		  System.out.println("Response Body is =>  " + responseBody);
		  // Status Code Validation
		  int statusCode = response.getStatusCode();
//		  System.out.println("Status code is =>  " + statusCode);
		  
		  JsonPath json=new JsonPath(response.asString());
		  String out_Token=json.getString("data.type");
		  JsonPath js = new JsonPath(response.asString());
			String type_act = js.get("data.type");
			System.out.println(type_act);
			Assert.assertEquals(type_act, "country");
			
			
			String iso_act = js.get("data.attributes.iso_name");
			System.out.println(iso_act);
			Assert.assertEquals(iso_act, "INDIA");
		  
	  }
}
