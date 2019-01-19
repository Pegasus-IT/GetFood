package io.getfood.modules.getting_started;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.material.snackbar.Snackbar;

import io.getfood.R;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.FamilyControllerApi;
import io.getfood.data.swagger.models.Family;
import io.getfood.models.SwaggerApiError;
import io.getfood.modules.BaseActivity;
import io.getfood.modules.home.HomeActivity;

import static io.getfood.data.local.Globals.API_BASEURL;

public class QRScanActivity extends BaseActivity {

    private CodeScannerView scannerView;
    private CodeScanner codeScanner;
    private FamilyControllerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = findViewById(R.id.scanner_view);

        api = new FamilyControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
        codeScanner = new CodeScanner(this, scannerView);
        
        codeScanner.setDecodeCallback(result -> {
            if(result.getText().startsWith("getfood")) {
                validateCode(result.getText().split(":")[2]);
            }
        });

        scannerView.setOnClickListener(view -> codeScanner.startPreview());
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_scan_qr;
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

    @Override
    public void onBackPressed() {
        finish();
        QRScanActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void validateCode(String code) {
        new Thread(() -> {
            try {
                this.onFamilyJoined(api.familyControllerJoinFamily(code));
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                this.onError(err);
            }
        }).start();
    }

    private void onError(ApiException err) {
        SwaggerApiError swaggerApiError = SwaggerApiError.parse(err.getResponseBody());
        this.showSnackbar(swaggerApiError.getMessage(), android.R.color.holo_red_dark);
    }

    private void onFamilyJoined(Family family) {
        this.showSnackbar(String.valueOf(R.string.getting_started_join_successful), R.color.getfood_main_blue);
        openAcitivity(new Intent(this, HomeActivity.class));
    }

    private void showSnackbar(String text, int color) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }
}
