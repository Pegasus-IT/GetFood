package io.getfood.modules.auth.sign_up;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserCreateUpdateModel;
import io.getfood.models.ApiManager;
import io.getfood.util.UserUtil;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class SignUpPresenter implements SignUpContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final SignUpContract.View signUpView;
    private final UserControllerApi api;

    SignUpPresenter(@NonNull SignUpContract.View signUpView, SharedPreferences preferences) {
        this.signUpView = checkNotNull(signUpView, "signUpView cannot be null");
        this.signUpView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start Login Presenter");
        signUpView.setRegisterButtonEnabled(false);
    }

    @Override
    public void validate(String username, String password, String firstName, String lastName) {
        if(username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            signUpView.setRegisterButtonEnabled(false);
        } else {
            signUpView.setRegisterButtonEnabled(true);
        }
    }

    @Override
    public void register(String username, String password, String firstName, String lastName) {
        UserCreateUpdateModel userCreateUpdateModel = new UserCreateUpdateModel();
        userCreateUpdateModel.setEmail(username);
        userCreateUpdateModel.setPassword(password);
        userCreateUpdateModel.setFirstName(firstName);
        userCreateUpdateModel.setLastName(lastName);

        new Thread(() -> {
            try {
                User user = api.userControllerRegister(userCreateUpdateModel);
                signUpView.onRegister(user);
                UserUtil.saveUser(user, sharedPreferences);
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                signUpView.onError(err);
            }
        }).start();
    }
}
