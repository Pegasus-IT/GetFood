package io.getfood.modules.family;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class FamilyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FamilyFragment familyFragment =
                (FamilyFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (familyFragment == null) {
            // Create the fragment
            familyFragment = FamilyFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), familyFragment, R.id.contentFrame);
        }

        new FamilyPresenter(familyFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.family_activity;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.family;
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