package getfood.io.ui.main;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import getfood.io.R;
import getfood.io.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;

    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.fab);
        bottomAppBar = findViewById(R.id.bottom_navigation_view);

        // Main line for setting menu in bottom navigation menu
//        setSupportActionBar(bottomAppBar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Action button is clicked", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", null)
                        .show();
            }
        });

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }
}
