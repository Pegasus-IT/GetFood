package io.getfood.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ShowKeyboard implements Runnable {

    private final TextView input;
    private final Activity activity;

    public ShowKeyboard(TextView input, Activity activity) {
        this.input = input;
        this.activity = activity;
    }

    @Override
    public void run() {
        input.setFocusableInTouchMode(true);
  //      passwordInput.requestFocusFromTouch();
        input.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(input, 0);
    }
}