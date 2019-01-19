package io.getfood.modules.shopping_list;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.getfood.R;
import io.getfood.data.swagger.models.ListItem;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseActivity;

public class ShoppingListActivity extends BaseActivity {

    private ShoppingList selectedShoppingList;
    private ListView listView;
    private FloatingActionButton createItem, cameraButton;
    private ShoppingListAdapter shoppingListAdapter;

    private ArrayList<ListItem> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedShoppingList = (ShoppingList) getIntent().getSerializableExtra("selectedShoppingListItem");

        View view = findViewById(R.id.shopping_list_container);
        listView = findViewById(R.id.shopping_listview);
        createItem = findViewById(R.id.shopping_list_create_fab);
        cameraButton = findViewById(R.id.shopping_list_camera_fab);

        // Set view background base on Color
        view.setBackgroundColor(selectedShoppingList.getColor());

        toolbar.setTitle(selectedShoppingList.getListName());
        toolbar.setSubtitle(selectedShoppingList.getDate());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance_Title_White);
        toolbar.setSubtitleTextAppearance(this, R.style.ToolbarTextAppearance_Subtitle_White);

        //TODO: Replace with real data
        shoppingList.add(new ListItem("Item 01", false));
        shoppingList.add(new ListItem("Item 02", false));
        shoppingList.add(new ListItem("Item 03", true));
        shoppingList.add(new ListItem("Item 04", false));
        shoppingList.add(new ListItem("Item 05", false));
        shoppingList.add(new ListItem("Item 06", true));

        shoppingListAdapter = new ShoppingListAdapter(this, shoppingList);
        listView.setAdapter(shoppingListAdapter);

        listView.setOnItemClickListener((adapterView, view1, i, l) -> System.out.println(i));
        createItem.setOnClickListener(view12 -> createItemInput());
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

    private void createItemInput() {
        final EditText itemName = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.shopping_list_create_item_title)
                .setMessage(R.string.shopping_list_create_item_description)
                .setView(itemName)
                .setPositiveButton("Add", (dialog, whichButton) -> {
                    if(!itemName.getText().toString().isEmpty()) {
                        createNewListItem(itemName.getText().toString());
                    } else {
                        createItemInput();
                    }
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> {})
                .show();
    }

    private void createNewListItem(String itemName) {
        shoppingList.add(0, new ListItem(itemName, false));
        shoppingListAdapter.notifyDataSetChanged();

        //TODO: Add item to list in API logic
    }
}
