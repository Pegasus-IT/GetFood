package io.getfood.modules.home;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.api.UserControllerApi;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class HomePresenter implements HomeContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final HomeContract.View homeView;
    private final UserControllerApi api;

    HomePresenter(@NonNull HomeContract.View homeView, SharedPreferences preferences) {
        this.homeView = checkNotNull(homeView, "homeView cannot be null");
        this.homeView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
    }

    @Override
    public void start() {
        System.out.println("Start Home Presenter");

    }
}
