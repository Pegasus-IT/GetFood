package io.getfood.modules.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.getfood.R;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseActivity;

public class HomeActivity extends BaseActivity {

    private FloatingActionButton createListButton;

    private ListView listView;
    private HomeListAdapter homeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createListButton = findViewById(R.id.home_create_list_fab);
        listView = findViewById(R.id.home_listview);

        toolbar.setNavigationIcon(R.drawable.ic_menu_34);

        createListButton.setOnClickListener(v -> {
            
        });

        View topPadding = new View(this);
        topPadding.setMinimumHeight(20);


        //TODO: Replace with real data
        ArrayList<ShoppingList> shoppingList = new ArrayList<>();
        shoppingList.add(new ShoppingList("Feest", "15 Jan", Color.parseColor("#427CFB"), 8, 2));
        shoppingList.add(new ShoppingList("Week lijst", "15 Jan", Color.parseColor("#00D157"), 18, 6));
        shoppingList.add(new ShoppingList("Weekend", "15 Jan", Color.parseColor("#FFBB00"), 3, 1));
        shoppingList.add(new ShoppingList("Feest", "15 Jan", Color.parseColor("#427CFB"), 12, 7));
        shoppingList.add(new ShoppingList("Week lijst", "15 Jan", Color.parseColor("#00D157"), 9, 4));
        shoppingList.add(new ShoppingList("Weekend", "15 Jan", Color.parseColor("#FFBB00"), 22, 16));


        homeListAdapter = new HomeListAdapter(this, shoppingList);
        listView.setAdapter(homeListAdapter);
        listView.addHeaderView(topPadding);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            ShoppingList selectedShoppingList = (ShoppingList) adapterView.getItemAtPosition(i);
            openShoppingListItem(selectedShoppingList);
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.home_title;
    }

    @Override
    protected int getToolbarNavigationIcon() {
        return R.drawable.ic_menu_34;
    }

    @Override
    protected int getOptionsMenu() {
        return R.menu.toolbar_home_menu;
    }


    private void openShoppingListItem(ShoppingList item) {
        Intent intent = new Intent(HomeActivity.this, ShoppingListActivity.class);
        intent.putExtra("selectedShoppingListItem", item);
        openAcitivity(intent, true);
    }
}
