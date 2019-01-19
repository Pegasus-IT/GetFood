package io.getfood.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import io.getfood.data.local.Globals;
import io.getfood.data.swagger.models.User;

public class UserUtil {
    public static void saveUser(User user, SharedPreferences preferences) {
        System.out.println("Saving user...");
        System.out.println("Token: " + user.getToken());

        if(user.getToken() != null) {
            PreferenceHelper.save(preferences, Globals.PrefKeys.UTOKEN, user.getToken());
            PreferenceHelper.save(preferences, Globals.PrefKeys.LOGIN_STATUS, true);
            PreferenceHelper.save(preferences, Globals.PrefKeys.LOGIN_USERNAME, user.getEmail());
            PreferenceHelper.save(preferences, Globals.PrefKeys.LOGIN_USER, new Gson().toJson(user));
        }
    }

    public static User getUser(SharedPreferences preferences) {
        boolean loginStatus = PreferenceHelper.read(preferences, Globals.PrefKeys.LOGIN_STATUS, false);

        if(loginStatus) {
            String jsonUser = PreferenceHelper.read(preferences, Globals.PrefKeys.LOGIN_USER, "");

            if(!jsonUser.isEmpty()) {
                User user = null;

                try {
                    user = new Gson().fromJson(jsonUser, User.class);
                } catch(Exception exception) {
                    exception.printStackTrace();
                    PreferenceHelper.remove(preferences, Globals.PrefKeys.LOGIN_USER);
                }

                return user;
            }
        }

        return null;
    }
}
