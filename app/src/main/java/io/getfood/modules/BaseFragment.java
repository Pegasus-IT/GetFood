package io.getfood.modules;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
    public void showSnackbar(String text, int color) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }
}
