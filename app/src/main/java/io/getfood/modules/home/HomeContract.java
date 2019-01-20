package io.getfood.modules.home;

import java.util.ArrayList;

import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        /**
         * Display the all the shopping list the family currently has
         * @param lists listModel
         */
        void setLists(ArrayList<ListModel> lists);

        /**
         * Display creation Snackbar
         * @param listModel listModel
         */
        void onListCreate(ListModel listModel);
    }

    interface Presenter extends BasePresenter {

        /**
         * Creates a new shopping list
         * @param listTitle string
         */
        void createNewList(String listTitle);
    }
}
