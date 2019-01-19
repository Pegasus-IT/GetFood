
package io.getfood.models;


import android.widget.Toast;

import net.steamcrafted.loadtoast.LoadToast;

import io.getfood.data.swagger.ApiException;

public interface BaseView<T> {

    void setPresenter(T presenter);
    void onError(ApiException err);
    void onError(String err);
    LoadToast createToast(String text);

}
