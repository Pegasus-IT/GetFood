package io.getfood.modules;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.steamcrafted.loadtoast.LoadToast;

import androidx.fragment.app.Fragment;
import io.getfood.R;
import io.getfood.data.swagger.ApiException;
import io.getfood.models.SwaggerApiError;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseFragment extends Fragment {
    public void showSnackbar(String text, int color) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(color))
                .show();
    }

    public void onError(String error) {
        showSnackbar(error, android.R.color.holo_red_dark);
    }

    public void onError(ApiException err) {
        SwaggerApiError swaggerApiError = SwaggerApiError.parse(err.getResponseBody());
        onError(swaggerApiError.getMessage());
    }

    public LoadToast createToast(String text) {
        LoadToast toast = new LoadToast(checkNotNull(getContext(), "Context is required."));
        toast.setText(text);
        toast.show();

        return toast;
    }


    public void openActivity(Intent intent) {
        startActivity(intent);
    }

    public void openActivity(Intent intent, boolean animated) {
        startActivity(intent);

        if (animated)
            checkNotNull(getActivity(), "Cannot find active activity").overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
