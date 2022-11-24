package testCases;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateOneProduct {
	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\CreatePayload.json";
	String firstProductId;
	String readOneProductId;
	
	HashMap<String, String> createPayloadMap = new HashMap<String, String>();
	
	
	public Map<String, String> createPayloadMap() {
		createPayloadMap.put("name", "Dream comes true By JR");
		createPayloadMap.put("price", "67");
		createPayloadMap.put("description", "The best seller in NY");
		createPayloadMap.put("category_id", "4");
		createPayloadMap.put("category_name", "Electronics");
		
		return createPayloadMap;
		
		
	}
	
	@Test(priority = 1)
	public void createOneProduct(){
		Response response = 
			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123")
				//.body(new File(filePath)).
				.body(createPayloadMap()).
			when()
				.put("/create.php").
			then()
				.extract().response();
			
	int ResponseCode = response.getStatusCode();
	System.out.println("The response code is: " + ResponseCode);
	Assert.assertEquals(ResponseCode, 201);
	
	String ResponseContentT = response.getContentType();
	System.out.println("The content type is: " + ResponseContentT);
	Assert.assertEquals(ResponseContentT, "application/json; charset=UTF-8");
	
	long ResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
	System.out.println("The response time is: " + ResponseTime);
	
	if(ResponseTime<=2000) {
		System.out.println("The response time is within range");
		
	}else {
		System.out.println("The response time is out of range");
		
	}
		
	String ResponseBody = response.getBody().asString();
	System.out.println("Response body is: " + ResponseBody);
	
	JsonPath jp = new JsonPath(ResponseBody);
	String productMessage = jp.getString("message");
	System.out.println("Product Message: " + productMessage);
	Assert.assertEquals(productMessage, "Product was created.");
	
	}
	
	String BaseURI = "https://techfios.com/api-prod/api/product";
	
	@Test(priority = 2)
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
	
		
	String responseBody = response.getBody().asString();
	
	
	JsonPath jp = new JsonPath(responseBody);
	firstProductId = jp.getString("records[0].id");
	System.out.println("First product ID: " + firstProductId);
	
	if(firstProductId != null) {
		System.out.println("Response body contains first product ID.");
	}else {
		System.out.println("Response body does not contains first product ID.");
	}
	}
	
	
	
	
	
	@Test(priority = 3)
	public void readOneProduct() {
		readOneProductId = firstProductId;
		Response response = 
			given()
				.baseUri(BaseURI)
				.header("Content-Type", "application/json")
				.queryParam("id", readOneProductId)
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
		
		
		
		System.out.println("-------------------------------------------");
		System.out.println("Read one response body is: ");
		String Res = response.getBody().prettyPrint();
		
		JsonPath jp2 = new JsonPath(Res);
		String actualProductName = jp2.getString("name");
		String actualProductPrice = jp2.getString("price");
		String actualProductDescription = jp2.getString("description");
		System.out.println("Actual product name: " + actualProductName);
		System.out.println("Actual product price: " + actualProductPrice);
		System.out.println("Actual product description: " + actualProductDescription);
		
		String expectedProductName = createPayloadMap.get("name");
		String expectedProductPrice = createPayloadMap.get("price");
		String expectedProductDescription = createPayloadMap.get("description");
		Assert.assertEquals(actualProductName, expectedProductName);
		Assert.assertEquals(actualProductPrice, expectedProductPrice);
		Assert.assertEquals(actualProductDescription, expectedProductDescription);
		
	}
	
}
