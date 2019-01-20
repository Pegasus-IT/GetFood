package io.getfood.modules;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.models.User;
import io.getfood.modules.auth.profile.ProfileActivity;
import io.getfood.modules.family.FamilyActivity;
import io.getfood.modules.home.HomeActivity;
import io.getfood.util.PreferenceHelper;
import io.getfood.util.UserUtil;

public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    @Nullable
    public Toolbar toolbar;

    @BindView(R.id.home_drawer_layout)
    @Nullable
    public DrawerLayout menuDrawerLayout;

    @BindView(R.id.nav_view)
    @Nullable
    public NavigationView menuNavigationView;

    @Nullable
    public TextView navEmail;

    @Nullable
    public TextView navName;

    @Nullable
    public TextView navInitials;

    /**
     * Creating the activity
     * @param savedInstanceState bundle of information of the activity it started in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);

        if (menuNavigationView != null) {
            View header = menuNavigationView.getHeaderView(0);

            if (header != null) {
                navEmail = header.findViewById(R.id.nav_email);
                navName = header.findViewById(R.id.nav_name);
                navInitials = header.findViewById(R.id.nav_initials);
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE);
        if (UserUtil.isLoggedIn(sharedPreferences)) {
            User user = UserUtil.getUser(sharedPreferences);

            if (navEmail != null)
                navEmail.setText(user.getEmail());
            if (navName != null)
                navName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
            if (navInitials != null)
                navInitials.setText(user.getInitials());
        }

        if (toolbar != null) {
            toolbar.setTitle(getToolbarTitle());
            toolbar.setNavigationIcon(getToolbarNavigationIcon());

            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(this);
        }

        if (menuNavigationView != null)
            menuNavigationView.setNavigationItemSelectedListener(this);

    }

    protected abstract int getLayoutResourceId();

    protected abstract int getToolbarTitle();

    protected abstract int getToolbarNavigationIcon();

    protected abstract int getOptionsMenu();

    /**
     * When the default android back button has been pressed,
     * check if the menu drawer is other if so close it and return
     */
    @Override
    public void onBackPressed() {
        if (toolbar != null && menuDrawerLayout.isDrawerOpen(menuNavigationView)) {
            menuDrawerLayout.closeDrawer(menuNavigationView);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (getOptionsMenu() != 0)
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
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("openCreateList", "yes");
                startActivity(intent);
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

        if (animated)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    private void logoutUser() {
        PreferenceHelper.clearAll(this);
        openAcitivity(new Intent(this, MainActivity.class));
    }

    private void openMenu() {
        if (menuDrawerLayout.isDrawerOpen(menuNavigationView)) {
            menuDrawerLayout.closeDrawer(menuNavigationView);
        } else {
            menuDrawerLayout.openDrawer(menuNavigationView);
        }
    }
}
