package io.getfood.modules.shopping_list;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class ShoppingListActivity extends BaseActivity {

    /**
     * Creating the activity
     * @param savedInstanceState bundle of information of the activity it started in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShoppingListFragment shoppingListFragment =
                (ShoppingListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (shoppingListFragment == null) {
            // Create the fragment
            shoppingListFragment = ShoppingListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), shoppingListFragment, R.id.contentFrame);
        }

        new ShoppingListPresenter(shoppingListFragment, getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE));
    }

    /**
     * When the default android back button has been pressed, slide in the in and out animation
     */
    @Override
    public void onBackPressed() {
        finish();
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
        return R.layout.shopping_list_activity;
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
        return R.string.shopping_list_title;
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
        return R.drawable.ic_arrow_left;
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
