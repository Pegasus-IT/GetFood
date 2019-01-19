package getfood.io.ui.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import getfood.io.R;
import getfood.io.adapters.HomeListAdapter;
import getfood.io.adapters.ShoppingListAdapter;
import getfood.io.models.ListItem;
import getfood.io.models.ShoppingList;
import getfood.io.ui.BaseActivity;

public class ShoppingListActivity extends BaseActivity {

    private ShoppingList selectedShoppingList;
    private ListView listView;
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedShoppingList = (ShoppingList) getIntent().getSerializableExtra("selectedShoppingListItem");

        View view = findViewById(R.id.shopping_list_container);
        listView = findViewById(R.id.shopping_listview);

        // Set view background base on Color
        view.setBackgroundColor(selectedShoppingList.getColor());

        toolbar.setTitle(selectedShoppingList.getListName());
        toolbar.setSubtitle(selectedShoppingList.getDate());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance_Title_White);
        toolbar.setSubtitleTextAppearance(this, R.style.ToolbarTextAppearance_Subtitle_White);

        //TODO: Replace with real data
        ArrayList<ListItem> shoppingList = new ArrayList<>();
        shoppingList.add(new ListItem("Item 01", false));
        shoppingList.add(new ListItem("Item 02", false));
        shoppingList.add(new ListItem("Item 03", true));
        shoppingList.add(new ListItem("Item 04", false));
        shoppingList.add(new ListItem("Item 05", false));
        shoppingList.add(new ListItem("Item 06", false));


        shoppingListAdapter = new ShoppingListAdapter(this, shoppingList);
        listView.setAdapter(shoppingListAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> System.out.println(i));
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
