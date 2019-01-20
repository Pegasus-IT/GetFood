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

    /**
     * Presenter is the middleman or mediator between View and Model which hold responsibilities
     * of everything which has to deal with presentation logic in your application. In general
     * terms, Presenter does the job of querying your Model, updating the View while responding to
     * the user's interactions.
     *
     * @param shoppingListView the given view
     * @param preferences SharedPreferences
     */
    ShoppingListPresenter(@NonNull ShoppingListContract.View shoppingListView, SharedPreferences preferences) {
        this.shoppingListView = checkNotNull(shoppingListView, "shoppingListView cannot be null");
        this.shoppingListView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start() {
        System.out.println("Start ShoppingList Presenter");
    }
}
