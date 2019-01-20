package io.getfood.modules.getting_started;

import io.getfood.data.swagger.models.Family;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface GettingStartedContract {

    interface View extends BaseView<Presenter> {

        /**
         * Check for setting the buttons visible
         * @param state state
         */
        void setButtonsVisible(boolean state);

        /**
         * Navigate to the home activity
         */
        void onAlreadyInFamily();

        /**
         * After joining a family navigate to the home activity
         * @param familyControllerCreate family
         */
        void onFamilyJoined(Family familyControllerCreate);
    }

    interface Presenter extends BasePresenter {

        /**
         * Create a family
         * @param toString family name
         */
        void createFamilyWithName(String toString);
    }
}
