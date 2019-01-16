package getfood.io.ui.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import getfood.io.R;
import getfood.io.ui.createlist.CreateListActivity;

public class ShoppingListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Feest");
        toolbar.setSubtitle("Created on 15 Jan");
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
