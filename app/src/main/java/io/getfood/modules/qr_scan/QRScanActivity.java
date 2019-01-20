package io.getfood.modules.qr_scan;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class QRScanActivity extends BaseActivity {

    /**
     * Creating the activity
     * @param savedInstanceState bundle of information of the activity it started in
     */
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

    /**
     * When the default android back button has been pressed, slide in the in and out animation
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Gets the needed layout to use
     * For example:
     * R.layout.login_activity
     *
     * @return layout
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.scan_qr_activity;
    }

    /**
     * Gets the toolbar title
     * For example:
     * R.string.home_title
     *
     * @return toolbar title
     */
    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    /**
     * Gets the toolbar navigation icon
     * For example:
     * R.drawable.ic_menu_34
     *
     * @return toolbar navigation icon
     */
    @Override
    protected int getToolbarNavigationIcon() {
        return 0;
    }

    /**
     * Gets the toolbar menu options
     * For example:
     * R.menu.toolbar_shopping_list_menu
     *
     * @return menu options
     */
    @Override
    protected int getOptionsMenu() {
        return 0;
    }
}