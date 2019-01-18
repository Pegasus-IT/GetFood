package getfood.io.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import getfood.io.R;
import getfood.io.data.network.ApiException;
import getfood.io.data.network.api.UserControllerApi;
import getfood.io.models.SwaggerApiError;
import getfood.io.models.User;
import getfood.io.models.UserCreateModel;
import getfood.io.ui.BaseActivity;
import getfood.io.ui.login.LoginActivity;

public class SignUpActivity extends BaseActivity {

    private EditText emailInput, passwordInput, firstnameInput, lastnameInput;
    private Button registerButton;

    private UserControllerApi api;

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
            registerButton.setEnabled(emailInput.getText().length() != 0 &&
                    passwordInput.getText().length() != 0 &&
                    firstnameInput.getText().length() != 0 &&
                    lastnameInput.getText().length() != 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inputs
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        firstnameInput = findViewById(R.id.first_name);
        lastnameInput = findViewById(R.id.last_name);

        // Buttons
        registerButton = findViewById(R.id.register);

        emailInput.addTextChangedListener(requiredTextWatcher);
        passwordInput.addTextChangedListener(requiredTextWatcher);
        firstnameInput.addTextChangedListener(requiredTextWatcher);
        lastnameInput.addTextChangedListener(requiredTextWatcher);

        api = new UserControllerApi();

        registerButton.setOnClickListener((View v) -> register(
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                firstnameInput.getText().toString(),
                lastnameInput.getText().toString()));
        registerButton.setEnabled(false);

        View toLoginButton = findViewById(R.id.to_login);
        toLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sign_up;
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
        SignUpActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void register(String email, String password, String firstName, String lastName) {
        UserCreateModel request = new UserCreateModel();
        request.setEmail(email);
        request.setPassword(password);
        request.setFirstName(firstName);
        request.setLastName(lastName);

        new Thread(() -> {
            try {
                this.onRegister(api.userControllerRegister(request));
            } catch (ApiException err) {
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

    private void onRegister(User userControllerAuthenticate) {
        System.out.println("Account Created!");
        this.showSnackbar("Account Created!", android.R.color.holo_green_light);

        openAcitivity(new Intent(this, LoginActivity.class), true);
    }
}
