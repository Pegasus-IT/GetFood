package io.getfood.modules.getting_started;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiClient;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class GettingStartedPresenter implements GettingStartedContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final GettingStartedContract.View gettingStartedView;
    private final UserControllerApi api;

    GettingStartedPresenter(@NonNull GettingStartedContract.View gettingStartedView, SharedPreferences preferences) {
        this.gettingStartedView = checkNotNull(gettingStartedView, "gettingStartedView cannot be null");
        this.gettingStartedView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start GettingStarted Presenter");

    }
}
