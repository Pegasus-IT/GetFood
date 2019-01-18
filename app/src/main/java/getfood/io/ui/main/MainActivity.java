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

        if(PreferenceHelper.read(this, Globals.PrefKeys.LOGIN_STATUS, false)) {
            // User did login already
            intent = new Intent(this, HomeActivity.class);
        } else {
            // FirstRun
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
