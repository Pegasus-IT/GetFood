package getfood.io.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Map;

import getfood.io.R;
import getfood.io.data.network.ApiCallback;
import getfood.io.data.network.ApiException;
import getfood.io.data.network.api.UserControllerApi;
import getfood.io.models.User;
import getfood.io.models.UserAuthenticationRequest;
import getfood.io.models.UserCreateModel;
import getfood.io.util.PreferenceHelper;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText usernameInput, passwordInput;
    private Button loginButton;

    private UserControllerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.button_login);

        api = new UserControllerApi();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login(usernameInput.getText().toString(), passwordInput.getText().toString());
                } catch (ApiException e) {
                    e.printStackTrace();
                }
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

        api.userControllerAuthenticateAsync(request, new ApiCallback<User>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                System.out.println(e.getResponseBody());
            }

            @Override
            public void onSuccess(User result, int statusCode, Map<String, List<String>> responseHeaders) {
                System.out.println("Loggin Success!");
                System.out.println("Token: " + result.getToken());

                PreferenceHelper.save(LoginActivity.this, "utoken", result.getToken());
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
}
