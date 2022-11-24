package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;


public class ReadOneProduct {

	
/*HTTP method: GET
baseURI=https://techfios.com/api-prod/api/product
endPoint/resource=/read_one.php

Header/s:
Content-Type=application/json
QueryParam:
id=5573
Authorization:
basic auth = username,password
StatusCode=200*/
	
	String BaseURI = "https://techfios.com/api-prod/api/product";
	
	
	@Test
	public void readOneProduct() {
		
		Response response = 
			given()
				.baseUri(BaseURI)
				.header("Content-Type", "application/json")
				.queryParam("id", "163")
				.auth().preemptive().basic("demo@techfios.com", "abc123").
			when()
				.get("/read_one.php").
			then()
				.extract().response();
		
		int ResponseCode = response.getStatusCode();
		System.out.println("The response code is: " + ResponseCode);
		Assert.assertEquals(ResponseCode, 200);
			
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("The response time is: " + responseTime);
		
		if(responseTime<=2000) {
			System.out.println("The response time is in range");
		}else {
			System.out.println("The response time is out of range");
		}
		String responseContentT = response.getContentType();
		System.out.println("The content type is: " + responseContentT);
		
		String responseHeader = response.getHeader("Content-Type");
		System.out.println("The response header is: " + responseHeader);
		Assert.assertEquals(responseHeader, "application/json");
		
		System.out.println("-------------------------------------------");
		System.out.println("The body is: ");
		String Res = response.getBody().prettyPrint();
		
		
	}
	
}
