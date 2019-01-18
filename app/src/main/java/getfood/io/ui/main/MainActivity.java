package getfood.io.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import getfood.io.R;
import getfood.io.data.local.Globals;
import getfood.io.ui.home.HomeActivity;
import getfood.io.ui.login.LoginActivity;
import getfood.io.util.PreferenceHelper;

public class MainActivity extends Activity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        Intent intent;

//        if(PreferenceHelper.read(this, Globals.PrefKeys.LOGIN_STATUS, false)) {
            // User did login already
//            intent = new Intent(this, HomeActivity.class);
//        } else {
            // FirstRun
            intent = new Intent(this, LoginActivity.class);
//        }

        startActivity(intent);
        finish();
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.jesusm.kfingerprintmanager.KFingerprintManager;
//
//import org.jetbrains.annotations.NotNull;
//
//import getfood.io.R;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String KEY = "KEY";
//    private int dialogTheme;
//    private TextView messageText;
//    private EditText encryptionMessageEditText;
//    private Button encryptButton;
//    private Button decryptButton;
//    private String messageToDecrypt;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.auth);
//
//        messageText = (TextView) findViewById(R.id.message);
//
//        initClickListeners();
//    }
//
//    private void initClickListeners() {
//
//        findViewById(R.id.buttonAuthenticate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createFingerprintManagerInstance().authenticate(new KFingerprintManager.AuthenticationCallback() {
//                    @Override
//                    public void onAuthenticationSuccess() {
//                        messageText.setText("Successfully authenticated");
//                    }
//
//                    @Override
//                    public void onSuccessWithManualPassword(@NotNull String password) {
//                        messageText.setText("Manual password: " + password);
//                    }
//
//                    @Override
//                    public void onFingerprintNotRecognized() {
//                        messageText.setText("Fingerprint not recognized");
//                    }
//
//                    @Override
//                    public void onAuthenticationFailedWithHelp(@Nullable String help) {
//                        messageText.setText(help);
//                    }
//
//                    @Override
//                    public void onFingerprintNotAvailable() {
//                        messageText.setText("Fingerprint not available");
//                    }
//
//                    @Override
//                    public void onCancelled() {
//                        messageText.setText("Operation cancelled by user");
//                    }
//                }, getSupportFragmentManager());
//            }
//        });
//    }
//
//    private KFingerprintManager createFingerprintManagerInstance() {
//        KFingerprintManager fingerprintManager = new KFingerprintManager(this, KEY);
//        fingerprintManager.setAuthenticationDialogStyle(dialogTheme);
//        return fingerprintManager;
//    }
//
//}
