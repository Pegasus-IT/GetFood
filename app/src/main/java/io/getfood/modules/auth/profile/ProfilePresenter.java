package io.getfood.modules.auth.profile;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserAuthenticationRequest;
import io.getfood.modules.auth.login.LoginContract;
import io.getfood.util.PreferenceHelper;
import io.getfood.util.UserUtil;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final ProfileContract.View profileView;
    private final UserControllerApi api;

    ProfilePresenter(@NonNull ProfileContract.View profileView, SharedPreferences preferences) {
        this.profileView = checkNotNull(profileView, "profileView cannot be null");
        this.profileView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
    }

    @Override
    public void start() {
        System.out.println("Start Profile Presenter");

    }
}
