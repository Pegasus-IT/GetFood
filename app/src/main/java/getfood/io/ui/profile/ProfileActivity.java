package getfood.io.ui.profile;

import android.os.Bundle;

import getfood.io.R;
import getfood.io.ui.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
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
