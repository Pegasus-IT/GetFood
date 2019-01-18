package getfood.io.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jesusm.kfingerprintmanager.KFingerprintManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import getfood.io.R;
import getfood.io.data.local.Globals;
import getfood.io.data.network.ApiCallback;
import getfood.io.data.network.ApiException;
import getfood.io.data.network.api.UserControllerApi;
import getfood.io.models.User;
import getfood.io.models.UserAuthenticationRequest;
import getfood.io.ui.BaseActivity;
import getfood.io.ui.home.HomeActivity;
import getfood.io.ui.sign_up.SignUpActivity;
import getfood.io.util.PreferenceHelper;

public class LoginActivity extends BaseActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
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

        api = new UserControllerApi();
        loginButton.setOnClickListener((View v) -> {
            try {
                login(usernameInput.getText().toString(), passwordInput.getText().toString());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        });

        if(PreferenceHelper.read(this, Globals.PrefKeys.LOGIN_STATUS, false)) {
            fingerprintButton.setAlpha(1f);
            fingerprintButton.setEnabled(true);
            showFingerAuth();
        }

        fingerprintButton.setOnClickListener(view -> showFingerAuth());
        signupText.setOnClickListener(view -> openAcitivity(new Intent(this, SignUpActivity.class), true));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
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

    private void login(String username, String password) throws ApiException {
        UserAuthenticationRequest request = new UserAuthenticationRequest();
        request.setEmail(username);
        request.setPassword(password);

        api.userControllerAuthenticateAsync(request, new ApiCallback<User>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                System.out.println(e.getResponseBody());
            }

            @Override
            public void onSuccess(User result, int statusCode, Map<String, List<String>> responseHeaders) {
                System.out.println("Loggin Success!");
                System.out.println("Token: " + result.getToken());

                PreferenceHelper.save(LoginActivity.this, Globals.PrefKeys.UTOKEN, result.getToken());
                PreferenceHelper.save(LoginActivity.this, Globals.PrefKeys.LOGIN_STATUS, true);
                PreferenceHelper.save(LoginActivity.this, Globals.PrefKeys.LOGIN_USERNAME, request.getEmail());

                openAcitivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                System.out.println("upload");
            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                System.out.println("download");
            }
        });
    }

    private void showFingerAuth() {
        createFingerprintManagerInstance().authenticate(new KFingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSuccess() {
                openAcitivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onSuccessWithManualPassword(@NotNull String password) {
                try {
                    passwordInput.setText(password);
                    login(usernameInput.getText().toString(), password);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFingerprintNotRecognized() { }

            @Override
            public void onAuthenticationFailedWithHelp(@Nullable String help) { }

            @Override
            public void onFingerprintNotAvailable() { }

            @Override
            public void onCancelled() { }
        }, getSupportFragmentManager());
    }

    private KFingerprintManager createFingerprintManagerInstance() {
        return new KFingerprintManager(this, Globals.BIOMETRIC_KEY);
    }
}
