package io.getfood.modules.auth.profile;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserUpdateModel;
import io.getfood.models.ApiManager;
import io.getfood.util.PreferenceHelper;
import io.getfood.util.UserUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final SharedPreferences sharedPreferences;
    private final ProfileContract.View profileView;
    private final UserControllerApi api;

    /**
     * Presenter is the middleman or mediator between View and Model which hold responsibilities
     * of everything which has to deal with presentation logic in your application. In general
     * terms, Presenter does the job of querying your Model, updating the View while responding to
     * the user's interactions.
     *
     * @param profileView the given view
     * @param preferences SharedPreferences
     */
    ProfilePresenter(@NonNull ProfileContract.View profileView, SharedPreferences preferences) {
        this.profileView = checkNotNull(profileView, "profileView cannot be null");
        this.profileView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");

        api = new UserControllerApi();
        ApiManager.add(api.getApiClient(), preferences);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start() {
        System.out.println("Start Profile Presenter");
        this.load();
    }

    /**
     * @inheritDoc
     */
    private void load() {
        new Thread(() -> {
            try {
                User user = api.userControllerCurrentUser();
                profileView.onProfileLoad(user);
            } catch (ApiException err) {
                profileView.onError(err);
            }
        }).start();
    }

    /**
     * @param username  email address
     * @param firstName firstName
     * @param lastName  lastName
     * @inheritDoc
     */
    @Override
    public void validate(String username, String firstName, String lastName) {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            profileView.setUpdateButtonEnabled(false);
        } else {
            profileView.setUpdateButtonEnabled(true);
        }
    }

    /**
     * @param username  email address
     * @param password  password
     * @param firstName firstName
     * @param lastName  LastName
     * @inheritDoc
     */
    @Override
    public void update(String username, @Nullable String password, String firstName, String lastName) {
        new Thread(() -> {
            try {
                UserUpdateModel userUpdateModel = new UserUpdateModel();
                userUpdateModel.setEmail(username);
                if (password != null && !password.trim().equals("")) {
                    userUpdateModel.setPassword(password);
                }
                userUpdateModel.setFirstName(firstName);
                userUpdateModel.setLastName(lastName);

                User user = api.userControllerUpdate(userUpdateModel);
                UserUtil.saveUser(user, sharedPreferences);
                profileView.onProfileLoad(user);
                profileView.onProfileUpdate(user);
            } catch (ApiException err) {
                profileView.onError(err);
            }
        }).start();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void delete() {
        new Thread(() -> {
            try {
                api.userControllerDelete();
                PreferenceHelper.clearAll(sharedPreferences);
                profileView.onProfileDeleted();
            } catch (ApiException err) {
                profileView.onError(err);
            }
        }).start();
    }
}
