package getfood.io.ui.getting_started;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import getfood.io.R;
import getfood.io.ui.BaseActivity;
import getfood.io.ui.login.LoginActivity;

public class GettingStartedActivity extends BaseActivity {

    private TextView bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomText = findViewById(R.id.not_ready);

        bottomText.setOnClickListener(view -> openAcitivity(new Intent(this, LoginActivity.class), true));
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
}
