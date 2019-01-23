package io.getfood.modules;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import net.steamcrafted.loadtoast.LoadToast;

import androidx.fragment.app.Fragment;
import io.getfood.R;
import io.getfood.data.swagger.ApiException;
import io.getfood.models.SwaggerApiError;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseFragment extends Fragment {

    /**
     * Access the menu that is created if the getOptionsMenu is not null
     */
    public Menu menu;

    /**
     * Gets the toolbar menu options
     * For example:
     * R.menu.toolbar_shopping_list_menu
     */
    protected int getOptionsMenu() {
        return 0;
    }


    /**
     * @param menu
     * @return
     * @inheritDoc
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        System.out.println(getOptionsMenu());
        if (getOptionsMenu() != 0) {
            inflater.inflate(getOptionsMenu(), menu);
            super.onCreateOptionsMenu(menu,inflater);
        }
    }

    /**
     * Show a Snackbar with the given text and color
     *
     * @param text  for the Snackbar
     * @param color for the button
     */
    public void showSnackbar(String text, int color) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }

    /**
     * @param error
     * @inheritDoc
     */
    public void onError(String error) {
        showSnackbar(error, android.R.color.holo_red_dark);
    }

    /**
     * @param err
     * @inheritDoc
     */
    public void onError(ApiException err) {
        String body = err.getResponseBody();
        if (body != null && !body.trim().equals("")) {
            System.out.println(body);
            SwaggerApiError swaggerApiError = SwaggerApiError.parse(body);
            onError(swaggerApiError.getMessage());
        } else {
            onError(err.getMessage());
        }
    }

    /**
     * @param text
     * @return
     * @inheritDoc
     */
    public LoadToast createToast(String text) {
        LoadToast toast = new LoadToast(checkNotNull(getContext(), "Context is required."));
        toast.setText(text);
        toast.show();

        return toast;
    }


    /**
     * Open given activity
     *
     * @param intent intent
     */
    public void openActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * Open given activity
     *
     * @param intent
     * @param animated
     */
    public void openActivity(Intent intent, boolean animated) {
        openActivity(intent, animated, R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Open given activity
     *
     * @param intent
     * @param enterAnimation
     * @param exitAnimation
     */
    public void openActivity(Intent intent, int enterAnimation, int exitAnimation) {
        openActivity(intent, true, enterAnimation, exitAnimation);
    }

    /**
     * Open given activity
     *
     * @param intent
     * @param animated
     * @param enterAnimation
     * @param exitAnimation
     */
    public void openActivity(Intent intent, boolean animated, int enterAnimation, int exitAnimation) {
        startActivity(intent);

        if (animated)
            checkNotNull(getActivity(), "Cannot find active activity").overridePendingTransition(enterAnimation, exitAnimation);
    }

}
