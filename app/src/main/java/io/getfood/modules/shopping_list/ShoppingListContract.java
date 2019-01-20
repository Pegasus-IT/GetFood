package io.getfood.modules.shopping_list;

import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;
import io.getfood.models.ShoppingList;

public interface ShoppingListContract {

    interface View extends BaseView<Presenter> {
        void createItemInput();
        void createNewListItem(String itemName);
    }

    interface Presenter extends BasePresenter {

    }
}
