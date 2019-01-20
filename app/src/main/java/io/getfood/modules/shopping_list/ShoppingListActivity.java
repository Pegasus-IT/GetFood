package io.getfood.modules.shopping_list;

import android.content.Intent;
import android.os.Bundle;

import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseActivity;
import io.getfood.util.ActivityUtils;

public class ShoppingListActivity extends BaseActivity {

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

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.shopping_list_activity;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.shopping_list_title;
    }

    @Override
    protected int getToolbarNavigationIcon() {
        return R.drawable.ic_arrow_left;
    }

    @Override
    protected int getOptionsMenu() {
        return R.menu.toolbar_shopping_list_menu;
    }
}