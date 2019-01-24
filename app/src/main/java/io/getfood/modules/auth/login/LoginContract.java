package io.getfood.modules.auth.login;

import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface LoginContract {

    public interface View extends BaseView<Presenter> {

        /**
         * Navigate to getting started activity
         * @param user model
         */
        void onLogin(User user);

        void onTokenValidated(User user);

        /**
         * Sets the username to the input field
         * @param text username
         */
        void setUsernameText(String text);

        /**
         * Check for showing the finger authentication popup
         */
        void showFingerAuth();

        /**
         * Check for showing the finger print button
         */
        void showFingerPrintButton();

        /**
         * Check for hiding the finger print button
         */
        void hideFingerPrintButton();

        /**
         * Set login button state
         * @param state boolean for the button
         */
        void setLoginButtonEnabled(boolean state);

        /**
         * Set fingerprint button state
         * @param state boolean for the fingerprint button
         */
        void setFingerprintButtonEnabled(boolean state);
    }

    public interface Presenter extends BasePresenter {

        /**
         * Login the user with the given credentials
         * @param username mail input
         * @param password password input
         */
        void login(String username, String password);

        /**
         * Checks if the input fields are empty
         * @param username mail input
         * @param password password input
         */
        void checkValid(String username, String password);

        /**
         * Validates the stored user token
         */
        void validateStoredToken();

        /**
         * Check for disabling auto auth start
         * @param autoAuthStartState check for autoAuth
         */
        void setDisableAutoAuthStart(boolean autoAuthStartState);
    }

}
