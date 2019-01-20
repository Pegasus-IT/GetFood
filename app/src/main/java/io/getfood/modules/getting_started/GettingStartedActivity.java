package io.getfood.modules.getting_started;

import android.content.Intent;
import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.modules.auth.login.LoginActivity;
import io.getfood.modules.home.HomeActivity;
import io.getfood.util.ActivityUtils;

public class GettingStartedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GettingStartedFragment gettingStartedFragment =
                (GettingStartedFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (gettingStartedFragment == null) {
            // Create the fragment
            gettingStartedFragment = GettingStartedFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), gettingStartedFragment, R.id.contentFrame);
        }

        new GettingStartedPresenter(gettingStartedFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("disableAutoAuthStart", "yes");
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.getting_started_activity;
    }

    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    @Override
    protected int getToolbarNavigationIcon() {
        return R.drawable.ic_menu_34;
    }

    @Override
    protected int getOptionsMenu() {
        return 0;
    }
}