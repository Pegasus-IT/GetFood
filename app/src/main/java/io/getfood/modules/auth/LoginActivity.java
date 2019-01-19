package io.getfood.modules.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.jesusm.kfingerprintmanager.KFingerprintManager;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserAuthenticationRequest;
import io.getfood.models.SwaggerApiError;
import io.getfood.modules.BaseActivity;
import io.getfood.modules.home.HomeActivity;
import io.getfood.util.PreferenceHelper;

import static io.getfood.data.local.Globals.API_BASEURL;

public class LoginActivity extends BaseActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private TextWatcher requiredTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            loginButton.setEnabled(usernameInput.getText().length() != 0 && passwordInput.getText().length() != 0);
        }
    };
    private ImageButton fingerprintButton;
    private TextView signupText;

    private UserControllerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.button_login);
        signupText = findViewById(R.id.no_account);
        fingerprintButton = findViewById(R.id.button_fingerprint);

        usernameInput.setText(PreferenceHelper.read(this, Globals.PrefKeys.LOGIN_USERNAME, ""));
        fingerprintButton.setEnabled(false);

        usernameInput.addTextChangedListener(requiredTextWatcher);
        passwordInput.addTextChangedListener(requiredTextWatcher);

        api = new UserControllerApi();
        api.getApiClient().setBasePath(API_BASEURL);
        loginButton.setOnClickListener((View v) -> login(usernameInput.getText().toString(), passwordInput.getText().toString()));
        loginButton.setEnabled(false);

        if (PreferenceHelper.read(this, Globals.PrefKeys.LOGIN_STATUS, false)) {
            fingerprintButton.setAlpha(1f);
            fingerprintButton.setEnabled(true);
            showFingerAuth();
        }

        fingerprintButton.setOnClickListener(view -> showFingerAuth());
        signupText.setOnClickListener(view -> openAcitivity(new Intent(this, SignUpActivity.class), true));
    }

    @Override
    public void onBackPressed() {
        finish();
        LoginActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.login_activity;
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

    private void login(String username, String password) {
        UserAuthenticationRequest request = new UserAuthenticationRequest();
        request.setEmail(username);
        request.setPassword(password);

        new Thread(() -> {
            try {
                this.onLogin(api.userControllerAuthenticate(request));
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                this.onError(err);
            }
        }).start();
    }

    private void showSnackbar(String text, int color) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }

    private void onError(ApiException err) {
        SwaggerApiError swaggerApiError = SwaggerApiError.parse(err.getResponseBody());
        this.showSnackbar(swaggerApiError.getMessage(), android.R.color.holo_red_dark);
    }

    private void onLogin(User userControllerAuthenticate) {
        System.out.println("Login Success!");
        System.out.println("Token: " + userControllerAuthenticate.getToken());

        PreferenceHelper.save(LoginActivity.this, Globals.PrefKeys.UTOKEN, userControllerAuthenticate.getToken());
        PreferenceHelper.save(LoginActivity.this, Globals.PrefKeys.LOGIN_STATUS, true);
        PreferenceHelper.save(LoginActivity.this, Globals.PrefKeys.LOGIN_USERNAME, userControllerAuthenticate.getEmail());
        System.out.println(userControllerAuthenticate);
        openAcitivity(new Intent(this, HomeActivity.class), true);
    }

    private void showFingerAuth() {
        createFingerprintManagerInstance().authenticate(new KFingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSuccess() {
                openAcitivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onSuccessWithManualPassword(@NotNull String password) {
                passwordInput.setText(password);
                login(usernameInput.getText().toString(), password);
            }

            @Override
            public void onFingerprintNotRecognized() {
            }

            @Override
            public void onAuthenticationFailedWithHelp(@Nullable String help) {
            }

            @Override
            public void onFingerprintNotAvailable() {
            }

            @Override
            public void onCancelled() {
            }
        }, getSupportFragmentManager());
    }

    private KFingerprintManager createFingerprintManagerInstance() {
        return new KFingerprintManager(this, Globals.BIOMETRIC_KEY);
    }
}
