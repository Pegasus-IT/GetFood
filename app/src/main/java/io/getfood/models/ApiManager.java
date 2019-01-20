package io.getfood.models;

import android.content.SharedPreferences;

import java.util.ArrayList;

import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiClient;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.util.UserUtil;

public class ApiManager extends ApiClient {

    private static ArrayList<ApiClient> apiClients = new ArrayList<>();

    public static void setToken(String token) {
        System.out.println("Updated token " + token);
        for (ApiClient apiClient : apiClients) {
            apiClient.setApiKey(token);
        }
    }

    public static void add(ApiClient api, SharedPreferences preferences) {
        api.setBasePath(Globals.API_BASEURL);
        apiClients.add(api);

        if (UserUtil.isLoggedIn(preferences)) {
            setToken(UserUtil.getToken(preferences));
        }
    }
}
