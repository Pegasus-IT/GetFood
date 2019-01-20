package io.getfood.modules.shopping_list;

import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface ShoppingListContract {

    interface View extends BaseView<Presenter> {

        /**
         * Create item dialog
         */
        void createItemInput();

        /**
         * Create new item
         * @param itemName string
         */
        void createNewListItem(String itemName);
    }

    interface Presenter extends BasePresenter {

    }
}
