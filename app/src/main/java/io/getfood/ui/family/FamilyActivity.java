package io.getfood.ui.family;

import android.os.Bundle;

import io.getfood.R;
import io.getfood.ui.BaseActivity;

public class FamilyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_getting_started;
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
