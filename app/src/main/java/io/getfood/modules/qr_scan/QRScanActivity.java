package io.getfood.modules.qr_scan;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class QRScanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QRScanFragment qrCodeFragment =
                (QRScanFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (qrCodeFragment == null) {
            // Create the fragment
            qrCodeFragment = QRScanFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), qrCodeFragment, R.id.contentFrame);
        }

        new QRScanPresenter(qrCodeFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.scan_qr_activity;
    }

    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    @Override
    protected int getToolbarNavigationIcon() {
        return 0;
    }

    @Override
    protected int getOptionsMenu() {
        return 0;
    }
}