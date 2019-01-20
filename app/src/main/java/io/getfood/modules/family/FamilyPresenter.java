package io.getfood.modules.family;

import android.content.SharedPreferences;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.FamilyControllerApi;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.Family;
import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class FamilyPresenter implements FamilyContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final FamilyContract.View familyView;
    private final FamilyControllerApi api;

    /**
     * Presenter is the middleman or mediator between View and Model which hold responsibilities
     * of everything which has to deal with presentation logic in your application. In general
     * terms, Presenter does the job of querying your Model, updating the View while responding to
     * the user's interactions.
     *
     * @param familyView the given view
     * @param preferences SharedPreferences
     */
    FamilyPresenter(@NonNull FamilyContract.View familyView, SharedPreferences preferences) {
        this.familyView = checkNotNull(familyView, "familyView cannot be null");
        this.familyView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new FamilyControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start() {
        System.out.println("Start Family Presenter");
        this.load();
    }

    /**
     * Load the active family
     */
    private void load() {
        new Thread(() -> {
            try {
                Family family = api.familyControllerGetActiveFamily();
                familyView.onFamilyLoad(family);
            } catch (ApiException err) {
                familyView.onError(err);
            }
        }).start();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void leaveFamily() {
        new Thread(() -> {
            try {
                Family family = api.familyControllerLeaveActiveFamily();
                familyView.onFamilyLeave(family);
            } catch (ApiException err) {
                familyView.onError(err);
            }
        }).start();
    }
}
