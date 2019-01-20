package io.getfood.modules.qr_scan;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.FamilyControllerApi;
import io.getfood.models.ApiManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class QRScanPresenter implements QRScanContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final QRScanContract.View qrScanView;
    private final FamilyControllerApi api;

    QRScanPresenter(@NonNull QRScanContract.View homeView, SharedPreferences preferences) {
        this.qrScanView = checkNotNull(homeView, "qrScanView cannot be null");
        this.qrScanView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new FamilyControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start Home Presenter");

    }

    @Override
    public void validateCode(String code) {
        new Thread(() -> {
            try {
                qrScanView.onFamilyJoined(api.familyControllerJoinFamily(code));
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                qrScanView.onError(err);
            }
        }).start();
    }
}
