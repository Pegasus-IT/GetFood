package io.getfood.modules.getting_started;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.FamilyControllerApi;
import io.getfood.data.swagger.models.Family;
import io.getfood.data.swagger.models.FamilyCreateUpdate;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class GettingStartedPresenter implements GettingStartedContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final GettingStartedContract.View gettingStartedView;
    private final FamilyControllerApi api;

    /**
     * Presenter is the middleman or mediator between View and Model which hold responsibilities
     * of everything which has to deal with presentation logic in your application. In general
     * terms, Presenter does the job of querying your Model, updating the View while responding to
     * the user's interactions.
     *
     * @param gettingStartedView the given view
     * @param preferences SharedPreferences
     */
    GettingStartedPresenter(@NonNull GettingStartedContract.View gettingStartedView, SharedPreferences preferences) {
        this.gettingStartedView = checkNotNull(gettingStartedView, "gettingStartedView cannot be null");
        this.gettingStartedView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new FamilyControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start GettingStarted Presenter");

        gettingStartedView.setButtonsVisible(false);

        new Thread(() -> {
            try {
                Family family = api.familyControllerGetActiveFamily();
                System.out.println("Already in family");
                System.out.println(family.getName());
                gettingStartedView.setButtonsVisible(false);
                gettingStartedView.onAlreadyInFamily();
            } catch (ApiException err) {
                if(err.getCode() == 404) {
                    System.out.println(err.getResponseBody());
                    gettingStartedView.setButtonsVisible(true);
                } else {
                    gettingStartedView.onError(err);
                }
            }
        }).start();
    }

    @Override
    public void createFamilyWithName(String familyName) {
        FamilyCreateUpdate familyCreateUpdate = new FamilyCreateUpdate();
        familyCreateUpdate.setName(familyName);

        new Thread(() -> {
            try {
                gettingStartedView.onFamilyJoined(api.familyControllerCreate(familyCreateUpdate));
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                gettingStartedView.onError(err);
            }
        }).start();
    }
}
