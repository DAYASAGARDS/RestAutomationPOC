package testClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import cucumber.api.DataTable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import utility.RestAPIUtilities;

public class TestClass {



	private static RequestSpecification requestSpec;
	private static Response response;
	private static RequestSpecification postRequest;

	public void setRequesWithParameters(DataTable datatable) {

		List<List<String>> data = datatable.asLists(String.class);

		requestSpec = RestAssured.given().pathParam(data.get(0).get(0), data.get(0).get(1))
				.pathParam(data.get(1).get(0), data.get(1).get(1)).pathParam(data.get(2).get(0), data.get(2).get(1))
				.pathParam(data.get(3).get(0), data.get(3).get(1)).pathParam(data.get(4).get(0), RestAPIUtilities.apikey);
		RestAPIUtilities.reportStep("PASS", " Get Request is Set with parameters ''"+datatable+"'' ");
	}

	public static RequestSpecification getRequestWithParameters() {

		return requestSpec;
	}

	public void setPostReqwithParameters(String jsonFileName) throws IOException{
		 String jsonBody = generateStringFromResource(System.getProperty("user.dir") + "/src/test/resources/Search.json");
		   
		     postRequest = RestAssured
		    		.given()
		    		.contentType("application/json")
		            .body(jsonBody);
		     RestAPIUtilities.reportStep("PASS", " Post Request is Set with Json file ''"+jsonFileName+"'' with Json Body '"+jsonBody+"'  ");
	}
	
	public static RequestSpecification getPostRequest(){
		return postRequest;	
	}
	
	
	public void setCreateresponse(String method) {
		
		switch(method) {
		   case "GET" :
				response = getRequestWithParameters().when()
						  .get("/place/nearbysearch/json?location={location}&radius={radius}&type={type}&keyword={keyword}&key={key}");
				 RestAPIUtilities.reportStep("PASS", "  Response is Set with for GET method and has Response Body -->" +response.body().asString());
				//int time = TestClass.waitUntilStringFoundInResponse(response, "Australian Cruise Group Circular Quay", 30);
				//System.out.println("took '" + time + " seconds to load");

		      break; 
		   
		   case "POST" :
			   response = getPostRequest().when()
			            .post("/place/add/json?key=" + RestAPIUtilities.apikey);
			   
			   RestAPIUtilities.reportStep("PASS", "  Response is Set with for POST method and has Response Body -->" +response.body().asString());
		      break;
		      
		   default : 
			   
		}

	}

	public static Response getCreateResponse() {
		return response;
	}

	public void verifyScopeAndStatusOfAdd(DataTable datatable){

		List<List<String>> data = datatable.asLists(String.class);

		ExtractableResponse<Response> result = getCreateResponse()
						.then().extract();
		System.out.println("ExtractableResponse is -->"+ result);
		
		String FirstRow = result.path(data.get(0).get(0));
		System.out.println("First row result is -->"+ FirstRow);

		if (FirstRow.equals(data.get(0).get(1))) {
			RestAPIUtilities.reportStep("PASS", "'"+data.get(0).get(0)+"' has ' "+FirstRow);
		}
		else {

			RestAPIUtilities.reportStep("FAIL", "'"+data.get(0).get(0)+"' has incorrect ' "+FirstRow);
		}
		
		
		String SecondRow = result.path(data.get(1).get(0));
		
		System.out.println("Second row result is -->"+ SecondRow);
		
		if (SecondRow.equals(data.get(1).get(1))) {
			RestAPIUtilities.reportStep("PASS", "'"+data.get(1).get(0)+"' has ' "+SecondRow);
		}
		else {

			RestAPIUtilities.reportStep("FAIL", "'"+data.get(1).get(0)+"' has incorrect ' "+SecondRow);
		}
		
	}
	
	public void verifyHotelResult(String responsepath, String HotelName) {

		ArrayList rest = getCreateResponse().then().extract().path(responsepath);

		System.out.println(rest.get(0));
		Assert.assertEquals(HotelName, rest.get(0), "PASS");

		RestAPIUtilities.reportStep("PASS", "Response Path '"+responsepath+"' with '"+ HotelName +"' is equal to '"+rest.get(0));
		
	}

	public void verifyStatusCodeis200() {
		int statusCode = getCreateResponse().getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
		RestAPIUtilities.reportStep("PASS", "Status code is ' " +statusCode);

	}

	public void verifyThereAreResults(int result){
		JsonPath jsonPathEvaluator = getCreateResponse().jsonPath();
		List<String> results = jsonPathEvaluator.get("results.name");
		Assert.assertEquals(result, results.size(), "PASS");
		RestAPIUtilities.reportStep("PASS", "Result Size is ' " +results.size());
		
	}

	public void post() throws IOException {

		   String jsonBody = generateStringFromResource(System.getProperty("user.dir") + "/src/test/resources/Search.json");
		   
		    String lis = RestAssured
		    		.given()
		    		.contentType("application/json").
		            body(jsonBody).
		    when().
		            post("https://maps.googleapis.com/maps/api/place/add/json?key=AIzaSyDjqbrxabdjAXtaw5jnnkknJKaKnadOxhA").
		    then().extract().path("scope","id");
		   // path("id");
		    System.out.println(lis);
		        //   .statusCode(200).body("greeting.firstName", equalTo("Johan")).
		          //  body(cont);
		}
	
	public String generateStringFromResource(String path) throws IOException {

	    return new String(Files.readAllBytes(Paths.get(path)));

	}
	
	public static int waitUntilStringFoundInResponse(Response rest, String text, int TIMEOUT) {
		int i = 0;
		while (i < TIMEOUT) {
			String responseString = rest.asString();

			if (responseString.contains(text)) {
				break;
			} else {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
				++i;
				if (i == TIMEOUT) {

					try {
						throw new TimeoutException("Timed out after waiting for " + i + " seconds");
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return i;
	}

/*	
	 * public static Response createresponse(DataTable datatable) {
	 * 
	 * RestAssured.baseURI = "https://maps.googleapis.com/maps/api";
	 * 
	 * List<List<String>> data = datatable.asLists(String.class); Response rest
	 * = RestAssured.given().pathParam(data.get(0).get(0), data.get(0).get(1))
	 * .pathParam(data.get(1).get(0),
	 * data.get(1).get(1)).pathParam(data.get(2).get(0), data.get(2).get(1))
	 * .pathParam(data.get(3).get(0),
	 * data.get(3).get(1)).pathParam(data.get(4).get(0), data.get(4).get(1))
	 * .when() .get(
	 * "/place/nearbysearch/json?location={location}&radius={radius}&type={type}&keyword={keyword}&key={key}"
	 * );
	 * 
	 * int time = TestClass.waitUntilStringFoundInResponse(rest,
	 * "Australian Cruise Group Circular Quay", 30); System.out.println(
	 * "took ''''" + time + "'''' seconds to load"); return rest;
	 * 
	 * }
	 

	
	 * ArrayList rest = RestAssured .given() .pathParam("location",
	 * "-33.8670522,151.1957362") .pathParam("radius", "500")
	 * .pathParam("type", "restaurant") .pathParam("keyword", "cruise")
	 * .pathParam("key", "AIzaSyDjqbrxabdjAXtaw5jnnkknJKaKnadOxhA") .when()
	 * .get(
	 * "/place/nearbysearch/json?location={location}&radius={radius}&type={type}&keyword={keyword}&key={key}")
	 
	
	
	 * RequestSpecification httpRequest = RestAssured.given();
	 * RequestSpecification Parameters = httpRequest.pathParam("",
	 * "").pathParam("", ""); Response response =
	 * httpRequest.when().request(Method.GET, "/nearbysearch/json"); /* //
	 * Reader header of a give name. In this line we will get // Header named
	 * Content-Type String contentType = response.header("Content-Type");
	 * System.out.println("Content-Type value: " + contentType);
	 * 
	 * 
	 * String responseBody = response.getBody().asString();
	 * 
	 * System.out.println("Response Body is =>  " + responseBody); int
	 * statusCode = response.getStatusCode(); Assert.assertEquals(statusCode
	 * actual value, 200 expected value, "Correct status code returned"); // can
	 * give 400 to get proper code if u want to fail test case
	 * 
	 
	
	
	 * //RestAssured.baseURI = "https://maps.googleapis.com/maps/api"; int
	 * int statusCode = RestAssured .given() .pathParam("location",
	 * "-33.8670522,151.1957362") .pathParam("radius", "500")
	 * .pathParam("type", "restaurant") .pathParam("keyword", "cruise")
	 * .pathParam("key", "AIzaSyDjqbrxabdjAXtaw5jnnkknJKaKnadOxhA") .when()
	 * .get(
	 * "/place/nearbysearch/json?location={location}&radius={radius}&type={type}&keyword={keyword}&key={key}")
	 * .getStatusCode();
	 


	// System.out.println(RestAssured.when().get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyDjqbrxabdjAXtaw5jnnkknJKaKnadOxhA").then().extract().path("status"));



	public static RequestSpecification createRequestAndSendParameters(DataTable datatable) {

		RestAssured.baseURI = "https://maps.googleapis.com/maps/api";

		List<List<String>> data = datatable.asLists(String.class);

		RequestSpecification request = RestAssured.given().pathParam(data.get(0).get(0), data.get(0).get(1))
				.pathParam(data.get(1).get(0), data.get(1).get(1)).pathParam(data.get(2).get(0), data.get(2).get(1))
				.pathParam(data.get(3).get(0), data.get(3).get(1)).pathParam(data.get(4).get(0), data.get(4).get(1));

		return request;

	}

	*/
}
