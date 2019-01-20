package io.getfood.modules.auth.sign_up;

import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface SignUpContract {

    interface View extends BaseView<Presenter> {

        /**
         * @param err given error
         * @inheritDoc
         */
        void onError(ApiException err);

        /**
         * Navigate the user to the Getting Started activity
         * @param userControllerAuthenticate user
         */
        void onRegister(User userControllerAuthenticate);

        /**
         * Check for the register button to set it disabled or enabled
         *
         * @param state check
         */
        void setRegisterButtonEnabled(boolean state);
    }

    interface Presenter extends BasePresenter {

        /**
         * Validates the given credentials
         *
         * @param username  email address
         * @param password  password
         * @param firstName firstName
         * @param lastName  lastName
         */
        void validate(String username, String password, String firstName, String lastName);

        /**
         * Creates a new user with the given credentials
         *
         * @param username  email address
         * @param password  password
         * @param firstName firstName
         * @param lastName  lastName
         */
        void register(String username, String password, String firstName, String lastName);
    }

}
