package io.getfood.modules.family;

import io.getfood.data.swagger.models.Family;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface FamilyContract {

    interface View extends BaseView<Presenter> {
        void onFamilyLoad(Family family);
        void onFamilyLeave(Family family);
    }

    interface Presenter extends BasePresenter {
        void leaveFamily();
    }
}
