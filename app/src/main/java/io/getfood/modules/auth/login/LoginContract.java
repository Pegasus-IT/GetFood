package io.getfood.modules.auth.login;

import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        /**
         * Navigate to getting started activity
         * @param user model
         */
        void onLogin(User user);

        void onTokenValidated(User user);

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

        void validateStoredToken();

        void setDisableAutoAuthStart(boolean autoAuthStartState);
    }

}
