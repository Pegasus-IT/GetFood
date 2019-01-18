package getfood.io.ui.createlist;

import android.os.Bundle;

import getfood.io.R;
import getfood.io.ui.BaseActivity;

public class CreateListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_list;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.create_list_title;
    }

    @Override
    protected int getToolbarNavigationIcon() {
        return R.drawable.ic_arrow_left_black;
    }

    @Override
    protected int getOptionsMenu() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        finish();
        CreateListActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
