package io.getfood.modules.auth.login;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserAuthenticationRequest;
import io.getfood.util.PreferenceHelper;
import io.getfood.util.UserUtil;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class LoginPresenter implements LoginContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final LoginContract.View loginView;
    private final UserControllerApi api;

    LoginPresenter(@NonNull LoginContract.View loginView, SharedPreferences preferences) {
        this.loginView = checkNotNull(loginView, "loginView cannot be null");
        this.loginView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
    }

    @Override
    public void start() {
        System.out.println("Start Login Presenter");
        Boolean loginStatus = PreferenceHelper.read(this.sharedPreferences, Globals.PrefKeys.LOGIN_STATUS, false);

        if (loginStatus) {
            loginView.showFingerPrintButton();
            loginView.showFingerAuth();
            loginView.setLoginButtonEnabled(true);
            loginView.setUsernameText(PreferenceHelper.read(this.sharedPreferences, Globals.PrefKeys.LOGIN_USERNAME, ""));
        } else {
            loginView.hideFingerPrintButton();
            loginView.setLoginButtonEnabled(false);
        }

        loginView.setLoginButtonEnabled(false);
    }

    @Override
    public void login(String username, String password) {
        UserAuthenticationRequest request = new UserAuthenticationRequest();
        request.setEmail(username);
        request.setPassword(password);

        new Thread(() -> {
            try {
                User user = api.userControllerAuthenticate(request);
                loginView.onLogin(user);
                UserUtil.saveUser(user, sharedPreferences);
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                loginView.onError(err);
            }
        }).start();
    }

    @Override
    public void checkValid(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            loginView.setLoginButtonEnabled(false);
        } else {
            loginView.setLoginButtonEnabled(true);
        }
    }
}
