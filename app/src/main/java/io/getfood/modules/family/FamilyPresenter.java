package io.getfood.modules.family;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class FamilyPresenter implements FamilyContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final FamilyContract.View familyView;
    private final UserControllerApi api;

    FamilyPresenter(@NonNull FamilyContract.View familyView, SharedPreferences preferences) {
        this.familyView = checkNotNull(familyView, "familyView cannot be null");
        this.familyView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start Family Presenter");

    }
}
