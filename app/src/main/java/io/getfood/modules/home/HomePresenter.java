package io.getfood.modules.home;

import android.content.SharedPreferences;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.ListControllerApi;
import io.getfood.data.swagger.models.ListCreateUpdate;
import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final HomeContract.View homeView;
    private final ListControllerApi api;

    /**
     * Presenter is the middleman or mediator between View and Model which hold responsibilities
     * of everything which has to deal with presentation logic in your application. In general
     * terms, Presenter does the job of querying your Model, updating the View while responding to
     * the user's interactions.
     *
     * @param homeView    the given view
     * @param preferences SharedPreferences
     */
    HomePresenter(@NonNull HomeContract.View homeView, SharedPreferences preferences) {
        this.homeView = checkNotNull(homeView, "homeView cannot be null");
        this.homeView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new ListControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start() {
        System.out.println("Start Home Presenter");
        this.load();
    }

    /**
     * Load the shopping lists
     */
    private void load() {
        new Thread(() -> {
            try {
                ArrayList<ListModel> lists = new ArrayList<>(api.listControllerGetLists());
                homeView.setLists(lists);
            } catch (ApiException err) {
                homeView.onError(err);
            }
        }).start();
    }

    /**
     * @param listTitle string
     * @inheritDoc
     */
    @Override
    public void createNewList(String listTitle) {
        System.out.println(listTitle);
        ListCreateUpdate listCreateUpdate = new ListCreateUpdate();
        listCreateUpdate.setTitle(listTitle);

        new Thread(() -> {
            try {
                ListModel list = api.listControllerCreate(listCreateUpdate);
                load();
                homeView.onListCreate(list);
            } catch (ApiException err) {
                homeView.onError(err);
            }
        }).start();
    }
}
