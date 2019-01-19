package io.getfood.modules.getting_started;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.FamilyControllerApi;
import io.getfood.data.swagger.models.Family;
import io.getfood.data.swagger.models.FamilyCreateUpdate;
import io.getfood.models.ApiManager;
import io.getfood.models.SwaggerApiError;
import io.getfood.modules.BaseActivity;
import io.getfood.modules.home.HomeActivity;

public class OldGettingStartedActivity extends BaseActivity {

    private TextView bottomText;
    private RelativeLayout joinFamilyButton, createFamilyButton;
    private FamilyControllerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomText = findViewById(R.id.not_ready);
        joinFamilyButton = findViewById(R.id.join_family_button);
        createFamilyButton = findViewById(R.id.create_family_button);

        api = new FamilyControllerApi();
        ApiManager.add(api.getApiClient(), getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));

        bottomText.setOnClickListener(view -> onBackPressed());

        createFamilyButton.setOnClickListener(view -> createFamilyInput());
        joinFamilyButton.setOnClickListener(view -> {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                openCamera();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.getting_started_activity;
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    System.out.println("Permission Denied");
                    //TODO: Error handling toevoegen
                }
                return;
            }
        }
    }

    private void createFamilyInput() {
        final EditText familyName = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.getting_started_create_family)
                .setMessage(R.string.getting_started_create_family_description)
                .setView(familyName)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    if(!familyName.getText().toString().isEmpty()) {
                        createFamilyWithName(familyName.getText().toString());
                    } else {
                        createFamilyInput();
                    }
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> {})
                .show();
    }

    private void createFamilyWithName(String familyName) {
        FamilyCreateUpdate familyCreateUpdate = new FamilyCreateUpdate();
        familyCreateUpdate.setName(familyName);

        new Thread(() -> {
            try {
                this.onFamilyJoined(api.familyControllerCreate(familyCreateUpdate));
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

    private void openCamera() {
        openAcitivity(new Intent(this, QRScanActivity.class), true);
    }

}
