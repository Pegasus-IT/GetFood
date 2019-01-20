package io.getfood;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import io.getfood.modules.auth.sign_up.SignUpActivity;
import io.getfood.util.PreferenceHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;

public class SignUpTest {

    private Intent intent;

    private String testUsername = "test@test.com";
    private String testPassword = "test";
    private String testFirtname = "Test";
    private String testLastname = "Test";

    @Rule
    public ActivityTestRule<SignUpActivity> signUpActivity =
            new ActivityTestRule<>(SignUpActivity.class, true, false);

    @Before
    public void setUp() {
        Context targetContext = getInstrumentation().getTargetContext();
        PreferenceHelper.clearAll(targetContext);

        signUpActivity.launchActivity(intent);
    }

    @Test
    public void fillInSignUpInfo() {
        onView(withId(R.id.register)).check(matches(not(isEnabled())));
        fillInFieldInfo(R.id.email, testUsername);
        onView(withId(R.id.register)).check(matches(not(isEnabled())));
        fillInFieldInfo(R.id.password, testPassword);
        onView(withId(R.id.register)).check(matches(not(isEnabled())));
        fillInFieldInfo(R.id.first_name, testFirtname);
        onView(withId(R.id.register)).check(matches(not(isEnabled())));
        fillInFieldInfo(R.id.last_name, testLastname);
    }

    private void fillInFieldInfo(int id, String value) {
        onView(withId(id)).perform(clearText(), typeText(value));
        pauseTestFor(200);
    }

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
