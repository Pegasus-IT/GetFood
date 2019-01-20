package io.getfood.modules.getting_started;

import io.getfood.data.swagger.models.Family;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface GettingStartedContract {

    interface View extends BaseView<Presenter> {
        void setButtonsVisible(boolean state);
        void onAlreadyInFamily();
        void onFamilyJoined(Family familyControllerCreate);
    }

    interface Presenter extends BasePresenter {
        void createFamilyWithName(String toString);
    }
}
