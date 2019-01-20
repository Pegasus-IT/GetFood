package io.getfood.modules.qr_scan;

import io.getfood.data.swagger.models.Family;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface QRScanContract {

    interface View extends BaseView<Presenter> {

        /**
         * Navigate to the home activity
         * @param familyControllerJoinFamily Family
         */
        void onFamilyJoined(Family familyControllerJoinFamily);
    }

    interface Presenter extends BasePresenter {

        /**
         * Validate the given QR code
         * @param s string
         */
        void validateCode(String s);
    }
}
