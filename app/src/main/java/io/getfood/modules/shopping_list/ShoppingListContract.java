package io.getfood.modules.shopping_list;

import io.getfood.data.swagger.models.ListItem;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;
import io.getfood.models.ShoppingList;

public interface ShoppingListContract {

    interface View extends BaseView<Presenter> {
        void onLoad(ShoppingList shoppingList);
    }

    interface Presenter extends BasePresenter {
        void createNewListItem(String itemValue, ShoppingList shoppingList);
        void setMarked(ListItem listItem, ShoppingList shoppingList);
        void load(String id);
    }
}
