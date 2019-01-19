package io.getfood.modules.auth.profile;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProfileFragment profileFragment =
                (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (profileFragment == null) {
            // Create the fragment
            profileFragment = ProfileFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), profileFragment, R.id.contentFrame);
        }

        new ProfilePresenter(profileFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.profile_activity;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.profile;
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