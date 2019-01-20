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

    /**
     * Presenter is the middleman or mediator between View and Model which hold responsibilities
     * of everything which has to deal with presentation logic in your application. In general
     * terms, Presenter does the job of querying your Model, updating the View while responding to
     * the user's interactions.
     *
     * @param homeView    the given view
     * @param preferences SharedPreferences
     */
    QRScanPresenter(@NonNull QRScanContract.View homeView, SharedPreferences preferences) {
        this.qrScanView = checkNotNull(homeView, "qrScanView cannot be null");
        this.qrScanView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new FamilyControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start() {
        System.out.println("Start Home Presenter");

    }

    /**
     * @param code
     * @inheritDoc
     */
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
