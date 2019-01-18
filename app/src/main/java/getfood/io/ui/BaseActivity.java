package getfood.io.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import getfood.io.R;
import getfood.io.ui.family.FamilyActivity;
import getfood.io.ui.home.HomeActivity;
import getfood.io.ui.main.MainActivity;
import getfood.io.ui.profile.ProfileActivity;
import getfood.io.util.PreferenceHelper;

public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;

    private DrawerLayout menuDrawerLayout;
    private NavigationView menuNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        toolbar = findViewById(R.id.toolbar);
        menuDrawerLayout = findViewById(R.id.home_drawer_layout);
        menuNavigationView = findViewById(R.id.nav_view);

        if(toolbar != null) {
            toolbar.setTitle(getToolbarTitle());
            toolbar.setNavigationIcon(getToolbarNavigationIcon());

            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(this);
        }

        if(menuNavigationView != null)
            menuNavigationView.setNavigationItemSelectedListener(this);

    }

    protected abstract int getLayoutResourceId();
    protected abstract int getToolbarTitle();
    protected abstract int getToolbarNavigationIcon();
    protected abstract int getOptionsMenu();

    @Override
    public void onBackPressed() {
        if(toolbar != null && menuDrawerLayout.isDrawerOpen(menuNavigationView)) {
            menuDrawerLayout.closeDrawer(menuNavigationView);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if(getOptionsMenu() != 0)
            getMenuInflater().inflate(getOptionsMenu(), menu);

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (getToolbarNavigationIcon()) {
            case R.drawable.ic_menu_34:
                openMenu();
                break;
            default:
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                openAcitivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.nav_profile:
                openAcitivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_family:
                openAcitivity(new Intent(this, FamilyActivity.class));
                break;
            case R.id.nav_new_list:
                break;
            case R.id.nav_logout:
                logoutUser();
                break;
        }

        openMenu();
        return true;
    }

    public void openAcitivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    public void openAcitivity(Intent intent, boolean animated) {
        startActivity(intent);

        if(animated)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    private void logoutUser() {
        PreferenceHelper.clearAll(this);
        openAcitivity(new Intent(this, MainActivity.class));
    }

    private void openMenu() {
        if(menuDrawerLayout.isDrawerOpen(menuNavigationView)) {
            menuDrawerLayout.closeDrawer(menuNavigationView);
        } else {
            menuDrawerLayout.openDrawer(menuNavigationView);
        }
    }
}
