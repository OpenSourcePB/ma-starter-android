package az.pashabank.starter.test.robot

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import az.pashabank.starter.R
import az.pashabank.presentation.flow.main.MainActivity
import az.pashabank.starter.test.tools.ActivityScenarioHolder

class LoginScreenRobot {

    fun launchApp(scenarioHolder: ActivityScenarioHolder) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val intent = Intent(instrumentation.targetContext,MainActivity::class.java)
        scenarioHolder.launch(intent)
    }

    fun waitForMilliseconds(millisecond:Long) {
        Thread.sleep(millisecond)
    }

    fun selectEmailField() {
        onView(withId(R.id.inputEmail)).perform(click())
    }

    fun selectPasswordField() {
        onView(withId(R.id.inputPassword)).perform(click())
    }

    fun enterEmail(email: String) {
        onView(withId(R.id.inputEmail)).perform(typeText(email))
    }

    fun enterPassword(password: String) {
        onView(withId(R.id.inputPassword)).perform(typeText(password))
    }

    fun closeKeyboard() {
        Espresso.closeSoftKeyboard()
    }

    fun clickSignInButton() {
        onView(withId(R.id.btnLogin)).perform(click())
    }

    fun isSuccessfulLogin() {
        onView(withId(R.id.mainPageFragment)).check(matches(isDisplayed()))
    }
}
