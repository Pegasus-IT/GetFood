package io.getfood.modules.auth.profile;

import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        /**
         * Loads in the user information
         * @param user information
         */
        void onProfileLoad(User user);

        /**
         * Updates the user
         * @param user information
         */
        void onProfileUpdate(User user);

        /**
         * Sets update button state
         * @param state check
         */
        void setUpdateButtonEnabled(boolean state);

        /**
         * Navigate back to the login activity
         */
        void onProfileDeleted();
    }

    interface Presenter extends BasePresenter {

        /**
         * Checks if the inputs aren't empty
         * @param username email address
         * @param firstName firstName
         * @param lastName lastName
         */
        void validate(String username, String firstName, String lastName);

        /**
         * Updates the given inputs
         * @param username email address
         * @param password password
         * @param firstName firstName
         * @param lastName LastName
         */
        void update(String username, String password, String firstName, String lastName);

        /**
         * Deletes the current user
         */
        void delete();
    }
}
