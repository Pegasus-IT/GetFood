package getfood.io.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import getfood.io.R;
import getfood.io.adapters.HomeListAdapter;
import getfood.io.models.ShoppingList;
import getfood.io.ui.createlist.CreateListActivity;
import getfood.io.ui.shoppinglist.ShoppingListActivity;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton createListButton;

    private ListView listView;
    private HomeListAdapter homeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createListButton = findViewById(R.id.home_create_list_fab);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.home_listview);

        toolbar.setTitle(R.string.home_title);
        toolbar.setNavigationIcon(R.drawable.ic_menu_34);

        createListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreateListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList selectedShoppingList = (ShoppingList) adapterView.getItemAtPosition(i);
                openShoppingListItem(selectedShoppingList);
            }
        });

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.home_menu_action_search:
                Intent intent = new Intent(HomeActivity.this, ShoppingListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return true;
    }

    private void openShoppingListItem(ShoppingList item) {
        Intent intent = new Intent(HomeActivity.this, ShoppingListActivity.class);
        intent.putExtra("selectedShoppingListItem", (Serializable) item);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
