package io.getfood.modules.auth.login;

import android.text.Editable;

import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void onError(ApiException err);
        void onLogin(User userControllerAuthenticate);

        void setUsernameText(String text);

        void showFingerAuth();
        void showFingerPrintButton();
        void hideFingerPrintButton();
        void setLoginButtonEnabled(boolean state);
        void setFingerprintButtonEnabled(boolean state);
    }

    interface Presenter extends BasePresenter {

        void login(String username, String password);
        void checkValid(String username, String password);
    }

}
