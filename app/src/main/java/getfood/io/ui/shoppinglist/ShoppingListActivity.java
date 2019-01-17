package getfood.io.ui.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import getfood.io.R;
import getfood.io.models.ShoppingList;

public class ShoppingListActivity extends AppCompatActivity {

    private ShoppingList selectedShoppingList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        selectedShoppingList = (ShoppingList) getIntent().getSerializableExtra("selectedShoppingListItem");

        // Set view background base on Color
        View view = findViewById(R.id.shopping_list_container);
        view.setBackgroundColor(selectedShoppingList.getColor());

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(selectedShoppingList.getListName());
        toolbar.setSubtitle(selectedShoppingList.getDate());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance_Title_White);
        toolbar.setSubtitleTextAppearance(this, R.style.ToolbarTextAppearance_Subtitle_White);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_shopping_list_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        ShoppingListActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
