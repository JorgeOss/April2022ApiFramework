package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;


import java.util.concurrent.TimeUnit;



public class ReadAllProducts {
	String BaseURI = "https://techfios.com/api-prod/api/product";
	
	@Test
	public void readAllProducts() {
	Response response = 
		
    given()
		.baseUri(BaseURI)
		.header("Content-Type", "application/json; charset=UTF-8")
		.auth()
		.preemptive()
		.basic("demo@techfios.com", "abc123").
	when()
		.get("/read.php").
	then()
		.extract()
		.response();
	
	int responseCode = response.getStatusCode();
	System.out.println("Response code: " + responseCode);
	Assert.assertEquals(responseCode, 200);
	
	long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
	System.out.println("Response time: " + responseTime);
	if(responseTime<=2000) {
		System.out.println("Response time within range.");
	}else {
		System.out.println("Response time out of range.");
	}
	
	String responseHeader = response.getHeader("Content-Type");
	System.out.println("Response header: " + responseHeader);
	Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
	
	String responseBody = response.getBody().asString();
	System.out.println("Response body is: " +  responseBody);
	
	JsonPath jp = new JsonPath(responseBody);
	String firstProductId = jp.getString("records[0].id");
	System.out.println("First product ID: " + firstProductId);
	
	if(firstProductId != null) {
		System.out.println("Response body contains first product ID.");
	}else {
		System.out.println("Response body does not contains first product ID.");
	}
	}
}
