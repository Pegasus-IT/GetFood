package io.getfood.modules.shopping_list;

import android.content.SharedPreferences;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.ListControllerApi;
import io.getfood.data.swagger.api.ListItemControllerApi;
import io.getfood.data.swagger.models.ListCreateUpdate;
import io.getfood.data.swagger.models.ListItem;
import io.getfood.data.swagger.models.ListItemCreateUpdate;
import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.SerializableListItem;
import io.getfood.models.ShoppingList;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.getfood.data.local.Globals.API_BASEURL;

public class ShoppingListPresenter implements ShoppingListContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final ShoppingListContract.View shoppingListView;
    private final ListItemControllerApi listItemControllerApi;
    private final ListControllerApi listControllerApi;

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

        listItemControllerApi = new ListItemControllerApi();
        listItemControllerApi.getApiClient().setBasePath(API_BASEURL);
        listControllerApi = new ListControllerApi();
        listControllerApi.getApiClient().setBasePath(API_BASEURL);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start() {
        System.out.println("Start ShoppingList Presenter");
    }

    @Override
    public void load(String id) {
        new Thread(() -> {
            try {
                ListModel listModel = listControllerApi.listControllerGet(id);
                shoppingListView.onLoad(ShoppingList.parse(listModel));
            } catch (ApiException err) {
                shoppingListView.onError(err);
            }
        }).start();
    }

    @Override
    public void updateList(ShoppingList shoppingList) {
        new Thread(() -> {
            try {
                ListCreateUpdate listCreateUpdate = new ListCreateUpdate();
                listCreateUpdate.setTitle(shoppingList.getListName());
                ListModel listModel = listControllerApi.listControllerUpdate(shoppingList.getId(), listCreateUpdate);
                shoppingListView.onLoad(ShoppingList.parse(listModel));
            } catch (ApiException err) {
                shoppingListView.onError(err);
            }
        }).start();
    }

    @Override
    public void delete(ShoppingList shoppingList) {
        new Thread(() -> {
            try {
                boolean isDeleted = listControllerApi.listControllerDelete(shoppingList.getId());

                if (isDeleted) {
                    shoppingListView.onDelete();
                } else {
                    shoppingListView.onError("Failed deleting the list.");
                }
            } catch (ApiException err) {
                if (err.getCode() == 200) {
                    shoppingListView.onDelete();
                } else {
                    shoppingListView.onError(err);
                }
            }
        }).start();
    }

    @Override
    public void deleteCheckedItems(ShoppingList shoppingList, List<SerializableListItem> serializableListItems) {
        new Thread(() -> {
            try {
                for (SerializableListItem item : serializableListItems) {
                    if (item.isChecked()) {
                        listItemControllerApi.listItemControllerDelete(shoppingList.getId(), item.getId());
                    }
                }
                this.load(shoppingList.getId());
            } catch (ApiException err) {
                shoppingListView.onError(err);
            }
        }).start();
    }

    @Override
    public void createNewListItem(String itemValue, ShoppingList shoppingList) {
        new Thread(() -> {
            try {
                ListItemCreateUpdate listItemCreateUpdate = new ListItemCreateUpdate();
                listItemCreateUpdate.setName(itemValue);
                listItemCreateUpdate.setChecked(false);
                listItemControllerApi.listItemControllerCreate(shoppingList.getId(), listItemCreateUpdate);
                this.load(shoppingList.getId());
            } catch (ApiException err) {
                shoppingListView.onError(err);
            }
        }).start();
    }

    @Override
    public void setMarked(ListItem listItem, ShoppingList shoppingList) {
        new Thread(() -> {
            try {
                ListItemCreateUpdate listItemCreateUpdate = new ListItemCreateUpdate();
                listItemCreateUpdate.setName(listItem.getName());
                listItemCreateUpdate.setChecked(listItem.isChecked());
                listItemControllerApi.listItemControllerUpdate(shoppingList.getId(), listItem.getId(), listItemCreateUpdate);
                System.out.println("Updated!");
                this.load(shoppingList.getId());
                System.out.println("Loading!");
            } catch (ApiException err) {
                shoppingListView.onError(err);
            }
        }).start();
    }
}
