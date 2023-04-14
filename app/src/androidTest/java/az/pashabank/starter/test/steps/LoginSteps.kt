package az.pashabank.starter.test.steps

import az.pashabank.starter.test.tools.ActivityScenarioHolder
import az.pashabank.starter.test.robot.LoginScreenRobot
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class LoginSteps {

    private val robot = LoginScreenRobot()
    private val activityScenarioHolder = ActivityScenarioHolder()

    @Before
    fun setup() {
        // not needed now, but you may need to set up mock responses before your screen starts
    }

    @After
    fun tearDown() {
    }

    @Given("^I start the application$")
    fun i_start_app() {
        robot.launchApp(activityScenarioHolder)
    }

    @When("^I wait for (\\d+) milliseconds$")
    fun i_wait_for_milliseconds(millisecond: Long) {
        robot.waitForMilliseconds(millisecond)
    }

    @And("^I click email field$")
    fun i_click_email_field() {
        robot.selectEmailField()
    }

    @And("^I close the keyboard$")
    fun i_close_the_keyboard() {
        robot.closeKeyboard()
    }

    @And("^I enter valid email (\\S+)$")
    fun i_enter_valid_email(email: String) {
        robot.enterEmail(email)
    }

    @And("^I click password field$")
    fun i_click_password_field() {
        robot.selectPasswordField()
    }

    @And("^I enter valid password (\\S+)$")
    fun i_enter_valid_password(password: String) {
        robot.enterPassword(password)
    }

    @And("^I click sign in button$")
    fun i_click_sign_in_button() {
        robot.clickSignInButton()
    }

    @Then("^I expect to see main content page$")
    fun i_expect_to_see_see_main_content_page() {
        robot.isSuccessfulLogin()
    }

}
