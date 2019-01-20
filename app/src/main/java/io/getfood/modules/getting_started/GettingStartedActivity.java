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

    /**
     * Creating the activity
     * @param savedInstanceState bundle of information of the activity it started in
     */
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

    /**
     * When the default android back button has been pressed, slide in the in and out animation
     */
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("disableAutoAuthStart", "yes");
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Gets the needed layout to use
     * For example:
     * R.layout.login_activity
     *
     * @return layout
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.getting_started_activity;
    }

    /**
     * Gets the toolbar title
     * For example:
     * R.string.home_title
     *
     * @return toolbar title
     */
    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    /**
     * Gets the toolbar navigation icon
     * For example:
     * R.drawable.ic_menu_34
     *
     * @return toolbar navigation icon
     */
    @Override
    protected int getToolbarNavigationIcon() {
        return R.drawable.ic_menu_34;
    }

    /**
     * Gets the toolbar menu options
     * For example:
     * R.menu.toolbar_shopping_list_menu
     *
     * @return menu options
     */
    @Override
    protected int getOptionsMenu() {
        return 0;
    }
}