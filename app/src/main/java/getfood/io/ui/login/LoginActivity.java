package getfood.io.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import getfood.io.R;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {
        finish();
        LoginActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
