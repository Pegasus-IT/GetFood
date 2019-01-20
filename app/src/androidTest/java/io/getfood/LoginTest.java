package io.getfood;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import io.getfood.modules.auth.login.LoginActivity;
import io.getfood.util.PreferenceHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;

public class LoginTest {

    private Intent intent;

    private String testUsername = "test@test.com";
    private String testCorrectPassword = "test";
    private String testIncorrectPassword = "123";

    @Rule
    public ActivityTestRule<LoginActivity> loginActivity =
            new ActivityTestRule<>(LoginActivity.class, true, false);

    @Before
    public void setUp() {
        Context targetContext = getInstrumentation().getTargetContext();
        PreferenceHelper.clearAll(targetContext);

        loginActivity.launchActivity(intent);
    }

    @Test
    public void checkStringMatches() {
        onView(withText(R.string.login_welcome)).check(matches(isDisplayed()));
        onView(withText(R.string.login_punchline)).check(matches(isDisplayed()));
        onView(withText(R.string.login_username)).check(matches(isDisplayed()));
        onView(withText(R.string.login_password)).check(matches(isDisplayed()));
    }

    @Test
    public void incorrectLogin(){
        fillInLoginInfo(testIncorrectPassword);
        pauseTestFor(1000);
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("Email and/or Password is incorrect.")));
    }

    @Test
    public void correctLogin(){
        fillInLoginInfo(testCorrectPassword);
        onView(withId(R.id.button_login)).perform(click());
        onView(withText(R.string.getting_started_title)).check(matches(isDisplayed()));
    }

    private void fillInLoginInfo(String password) {
        onView(withId(R.id.button_login)).check(matches(not(isEnabled())));
        onView(withId(R.id.username)).perform(clearText(), typeText(testUsername));
        pauseTestFor(200);
        onView(withId(R.id.button_login)).check(matches(not(isEnabled())));
        onView(withId(R.id.password)).perform(clearText(), typeText(password));
        pauseTestFor(200);
        onView(withId(R.id.button_login)).check(matches(isEnabled()));
    }

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
