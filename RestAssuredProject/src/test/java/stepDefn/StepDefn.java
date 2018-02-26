package stepDefn;


import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import testClass.TestClass;
import utility.ExtentManager;
import utility.ExtentTestManager;


public class StepDefn {
	
	TestClass tc =new TestClass();

	@Before
    public void beforeScenario(Scenario scenario) {
           ExtentTestManager.startTest(scenario.getName());
    }
	
	@Given("^I Have BaseURI with below parameters:$")
	public void i_Have_BaseURI_with_below_parameters(DataTable datatable) throws Throwable {
		tc.setRequesWithParameters(datatable);
	}
	
	@When("^I want to \"([^\"]*)\" Request$")
	public void i_want_to_Request(String method) throws Throwable {
		tc.setCreateresponse(method);
	}
	
	@Then("^I should verify \"([^\"]*)\" Should have \"([^\"]*)\"$")
	public void i_should_verify_Should_have(String responsepath, String HotelName) throws Throwable {
		tc.verifyHotelResult(responsepath,HotelName);
		
	}
	
	@Then("^Verify status code is (\\d+)$")
	public void verify_status_code_is(int arg1) throws Throwable {
		tc.verifyStatusCodeis200();
	}
	
	@Then("^Verify there are (\\d+) results$")
	public void verify_there_are_results(int result) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		tc.verifyThereAreResults(result);
	}
	
	@Given("^I Have BaseURI with Json file \"([^\"]*)\"$")
	public void i_Have_BaseURI_with_Json_file(String jsonFileName) throws Throwable {
		tc.setPostReqwithParameters( jsonFileName);
	}

	@Then("^I verify that:$")
	public void i_verify_that(DataTable datatable) throws Throwable {
		tc.verifyScopeAndStatusOfAdd(datatable);
	}


	@After
    public void afterScenario(Scenario scenario) {
           ExtentTestManager.endTest();
           ExtentManager.getInstance().flush();
    }

}
