package io.getfood.modules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.getfood.R;
import io.getfood.modules.auth.login.LoginActivity;

public class MainActivity extends Activity {

    private static Context context;

    /**
     * @inheritDoc
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Main context
     * @return
     */
    public static Context getAppContext() {
        return MainActivity.context;
    }
}