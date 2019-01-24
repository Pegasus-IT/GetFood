package io.getfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserAuthenticationRequest;
import io.getfood.data.swagger.models.UserCreateModel;
import io.getfood.models.SwaggerApiError;
import io.getfood.modules.auth.login.LoginContract;
import io.getfood.modules.auth.login.LoginFragment;
import io.getfood.modules.auth.login.LoginPresenter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@PowerMockIgnore({"org.mockito.*"})
public class LoginTest {

    private LoginContract.View view;
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private LoginPresenter presenter;

    @Before // 2
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        view = Mockito.mock(LoginFragment.class);
        context = Mockito.mock(Context.class);
        prefs = Mockito.mock(SharedPreferences.class);
        editor = Mockito.mock(SharedPreferences.Editor.class);

        // get the presenter reference and bind with view for testing
        presenter = new LoginPresenter(view, prefs);
    }

    @Test
    public void usernamePasswordNotValid() {
        presenter.checkValid("", "");
        verify(view).setLoginButtonEnabled(false);
    }

    @Test
    public void usernamePasswordValid() {
        presenter.checkValid("test", "test");
        verify(view).setLoginButtonEnabled(true);
    }

    @Test
    public void loginInvalid() {
        UserAuthenticationRequest userAuthenticationRequest = new UserAuthenticationRequest();
        userAuthenticationRequest.setEmail("test");
        userAuthenticationRequest.setPassword("test");
        try {
            User user = presenter.getApi().userControllerAuthenticate(userAuthenticationRequest);
            assertNull(user);
        } catch (ApiException exception) {
            SwaggerApiError apiException = SwaggerApiError.parse(exception.getResponseBody());
            assertEquals("Not a valid email!", apiException.getMessage());
        }
    }

    @Test
    public void loginValid() {
        UserAuthenticationRequest userAuthenticationRequest = new UserAuthenticationRequest();
        String email = "john.doe@example.com";
        String password = "Test123!";

        userAuthenticationRequest.setEmail(email);
        userAuthenticationRequest.setPassword(password);
        try {
            User user = presenter.getApi().userControllerAuthenticate(userAuthenticationRequest);
            assertNotNull("User should not be null", user);
            assertEquals(email, user.getEmail());
        } catch (ApiException exception) {
            fail("ApiException should not have triggered");
        }
    }

    @Test
    public void userCreate() {
        UserCreateModel userCreateModel = new UserCreateModel();

        String firstName = "Test";
        String lastName = "Test";
        String email = "test-user-" + new DateTime().getMillis() + "@getfood-io.com";
        String password = "Test123!";

        userCreateModel.setFirstName(firstName);
        userCreateModel.setLastName(lastName);
        userCreateModel.setEmail(email);
        userCreateModel.setPassword(password);

        try {
            User user = presenter.getApi().userControllerRegister(userCreateModel);
            assertNotNull("User should not be null", user);
            assertEquals(firstName, user.getFirstName());
            assertEquals(lastName, user.getLastName());
            assertEquals(email, user.getEmail());
        } catch (ApiException exception) {
            fail("ApiException should not have triggered");
        }
    }
}