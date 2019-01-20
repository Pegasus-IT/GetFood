package io.getfood.modules.family;

import io.getfood.data.swagger.models.Family;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface FamilyContract {

    interface View extends BaseView<Presenter> {

        /**
         * Load in the active family
         * @param family family
         */
        void onFamilyLoad(Family family);

        /**
         * Navigate to the Getting Started activity
         * @param family family
         */
        void onFamilyLeave(Family family);
    }

    interface Presenter extends BasePresenter {

        /**
         * Leave the current family
         */
        void leaveFamily();
    }
}
