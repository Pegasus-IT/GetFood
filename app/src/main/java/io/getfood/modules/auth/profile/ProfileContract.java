package io.getfood.modules.auth.profile;

import android.text.Editable;

import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {
        void onProfileLoad(User user);
        void onProfileUpdate(User user);
        void setSignUpEnabled(boolean state);
        void onProfileDeleted();
    }

    interface Presenter extends BasePresenter {
        void validate(String username, String firstName, String lastName);
        void update(String username, String password, String firstName, String lastName);
        void delete();
    }
}
