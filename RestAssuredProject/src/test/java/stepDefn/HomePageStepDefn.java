package stepDefn;


import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utility.ExtentManager;
import utility.ExtentTestManager;


public class HomePageStepDefn {

	@Before
    public void beforeScenario(Scenario scenario) {
           ExtentTestManager.startTest(scenario.getName());
    }
	
	@Given("^I Have BaseURI$")
	public void i_Have_BaseURI() throws Throwable {
	    throw new PendingException();
	}

	@When("^I want to \"([^\"]*)\" Request and pass these parameters:$")
	public void i_want_to_Request_and_pass_these_parameters(String arg1, DataTable arg2) throws Throwable {

	    throw new PendingException();
	}

	@Then("^I should verify \"([^\"]*)\" Should be \"([^\"]*)\"$")
	public void i_should_verify_Should_be(String arg1, String arg2) throws Throwable {
	    throw new PendingException();
	}

	@After
    public void afterScenario(Scenario scenario) {
           ExtentTestManager.endTest();
           ExtentManager.getInstance().flush();
    }

}
