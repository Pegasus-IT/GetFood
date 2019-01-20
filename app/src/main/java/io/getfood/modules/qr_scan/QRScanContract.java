package io.getfood.modules.qr_scan;

import io.getfood.data.swagger.models.Family;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface QRScanContract {

    interface View extends BaseView<Presenter> {
        void onFamilyJoined(Family familyControllerJoinFamily);
    }

    interface Presenter extends BasePresenter {

        void validateCode(String s);
    }
}
