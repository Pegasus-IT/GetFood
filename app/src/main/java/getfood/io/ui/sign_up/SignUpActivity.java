package getfood.io.ui.sign_up;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import getfood.io.R;
import getfood.io.data.network.ApiException;
import getfood.io.models.UserCreateModel;
import getfood.io.ui.BaseActivity;

public class SignUpActivity extends BaseActivity {

    private EditText emailInput, passwordInput, firstnameInput, lastnameInput;
    private Button registerButton;

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

        registerButton.setOnClickListener((View v) -> {
            try {
                register(
                        emailInput.getText().toString(),
                        passwordInput.getText().toString(),
                        firstnameInput.getText().toString(),
                        lastnameInput.getText().toString());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        });

        View toLoginButton = findViewById(R.id.to_login);
        toLoginButton.setOnClickListener(v -> onBackPressed());
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

    private void register(String email, String password, String firstName, String lastName) throws ApiException {
        UserCreateModel request = new UserCreateModel();
        request.setEmail(email);
        request.setPassword(password);
        request.setFirstName(firstName);
        request.setLastName(lastName);
    }
}
