package io.getfood.modules.home;

import android.content.SharedPreferences;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.ListControllerApi;
import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final HomeContract.View homeView;
    private final ListControllerApi api;

    HomePresenter(@NonNull HomeContract.View homeView, SharedPreferences preferences) {
        this.homeView = checkNotNull(homeView, "homeView cannot be null");
        this.homeView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new ListControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start Home Presenter");
        this.load();
    }

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
}
