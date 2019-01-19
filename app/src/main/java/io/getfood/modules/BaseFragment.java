package io.getfood.modules;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;

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
