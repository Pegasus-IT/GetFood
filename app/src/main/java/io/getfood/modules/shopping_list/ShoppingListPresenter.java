package io.getfood.modules.shopping_list;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.models.ShoppingList;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class ShoppingListPresenter implements ShoppingListContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final ShoppingListContract.View shoppingListView;
    private final UserControllerApi api;

    ShoppingListPresenter(@NonNull ShoppingListContract.View shoppingListView, SharedPreferences preferences) {
        this.shoppingListView = checkNotNull(shoppingListView, "shoppingListView cannot be null");
        this.shoppingListView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
    }

    @Override
    public void start() {
        System.out.println("Start ShoppingList Presenter");
    }
}
