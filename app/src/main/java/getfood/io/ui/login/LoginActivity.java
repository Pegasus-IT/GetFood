package getfood.io.ui.login;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import getfood.io.R;
import getfood.io.data.network.ApiException;
import getfood.io.data.network.api.UserControllerApi;
import getfood.io.models.SwaggerApiError;
import getfood.io.models.User;
import getfood.io.models.UserAuthenticationRequest;
import getfood.io.util.PreferenceHelper;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private TextWatcher requiredTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}

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

    private UserControllerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.button_login);

        api = new UserControllerApi();

        loginButton.setEnabled(false);
        usernameInput.addTextChangedListener(requiredTextWatcher);
        passwordInput.addTextChangedListener(requiredTextWatcher);

        loginButton.setOnClickListener(clickEvent -> {
            try {
                login(usernameInput.getText().toString(), passwordInput.getText().toString());
            } catch (ApiException e) {

                e.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        LoginActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void login(String username, String password) throws ApiException {
        UserAuthenticationRequest request = new UserAuthenticationRequest();
        request.setEmail(username);
        request.setPassword(password);

        System.out.println(username);
        System.out.println(password);
        new Thread(() -> {
            try {
                this.onLogin(api.userControllerAuthenticate(request));
            } catch(ApiException err) {
                this.onError(err);
            }
        }).start();
    }

    public void showToast(String text, int color) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }

    private void onError(ApiException err) {
        SwaggerApiError swaggerApiError = SwaggerApiError.parse(err.getResponseBody());
        this.showToast(swaggerApiError.getMessage(), android.R.color.holo_red_dark);
    }

    private void onLogin(User userControllerAuthenticate) {
        PreferenceHelper.save(LoginActivity.this, "utoken", userControllerAuthenticate.getToken());
        System.out.println(userControllerAuthenticate);
    }
}
