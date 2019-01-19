package io.getfood.modules.auth.sign_up;

import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.models.User;
import io.getfood.models.BasePresenter;
import io.getfood.models.BaseView;

public interface SignUpContract {

    interface View extends BaseView<Presenter> {
        void onError(ApiException err);
        void onRegister(User userControllerAuthenticate);
        void setRegisterButtonEnabled(boolean state);
    }

    interface Presenter extends BasePresenter {
        void validate(String username, String password, String firstName, String lastName);
        void register(String username, String password, String firstName, String lastName);
    }

}
