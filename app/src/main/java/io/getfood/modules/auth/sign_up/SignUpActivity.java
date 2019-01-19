package io.getfood.modules.auth.sign_up;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SignUpFragment signUpFragment =
                (SignUpFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (signUpFragment == null) {
            // Create the fragment
            signUpFragment = SignUpFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), signUpFragment, R.id.contentFrame);
        }

        new SignUpPresenter(signUpFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.sign_up_activity;
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