package io.getfood.modules;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import io.getfood.data.swagger.ApiException;
import io.getfood.models.SwaggerApiError;

public class BaseFragment extends Fragment {
    public void showSnackbar(String text, int color) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }

    public void onError(ApiException err) {
        SwaggerApiError swaggerApiError = SwaggerApiError.parse(err.getResponseBody());
        showSnackbar(swaggerApiError.getMessage(), android.R.color.holo_red_dark);
    }
}
