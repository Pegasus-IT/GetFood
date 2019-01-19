package io.getfood.modules.shopping_list;

import android.os.Bundle;
import android.view.View;

import io.getfood.R;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseActivity;

public class ShoppingListActivity extends BaseActivity {

    private ShoppingList selectedShoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedShoppingList = (ShoppingList) getIntent().getSerializableExtra("selectedShoppingListItem");

        // Set view background base on Color
        View view = findViewById(R.id.shopping_list_container);
        view.setBackgroundColor(selectedShoppingList.getColor());

        toolbar.setTitle(selectedShoppingList.getListName());
        toolbar.setSubtitle(selectedShoppingList.getDate());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance_Title_White);
        toolbar.setSubtitleTextAppearance(this, R.style.ToolbarTextAppearance_Subtitle_White);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_shopping_list;
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

    @Override
    public void onBackPressed() {
        finish();
        ShoppingListActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
