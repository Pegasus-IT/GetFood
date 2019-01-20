package io.getfood.modules.home;

import java.util.ArrayList;

import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;
import io.getfood.models.ShoppingList;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void setLists(ArrayList<ListModel> lists);
        void onListCreate(ListModel listModel);
    }

    interface Presenter extends BasePresenter {
        void createNewList(String listTitle);
    }
}
