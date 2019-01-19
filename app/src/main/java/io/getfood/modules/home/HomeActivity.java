package io.getfood.modules.home;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomeFragment homeFragment =
                (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (homeFragment == null) {
            // Create the fragment
            homeFragment = HomeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.contentFrame);
        }

        new HomePresenter(homeFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.home_activity;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.home_title;
    }

    @Override
    protected int getToolbarNavigationIcon() {
        return R.drawable.ic_menu_34;
    }

    @Override
    protected int getOptionsMenu() {
        return R.menu.toolbar_home_menu;
    }
}