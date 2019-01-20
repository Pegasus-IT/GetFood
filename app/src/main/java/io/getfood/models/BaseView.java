package io.getfood.models;


import net.steamcrafted.loadtoast.LoadToast;

import io.getfood.data.swagger.ApiException;

public interface BaseView<T> {

    /**
     * Set the given presenter
     * @param presenter given presenter
     */
    void setPresenter(T presenter);

    /**
     * Displays the ApiException in a Snackbar
     * @param err given error
     */
    void onError(ApiException err);

    /**
     * Displays the String in a Snackbar
     * @param err given string for error
     */
    void onError(String err);

    /**
     * Creates a toast based on the string
     * @param text displaying text
     * @return toast
     */
    LoadToast createToast(String text);
}
