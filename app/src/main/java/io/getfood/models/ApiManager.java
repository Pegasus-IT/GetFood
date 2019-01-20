package io.getfood.models;

import android.content.SharedPreferences;

import java.util.ArrayList;

import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiClient;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.util.UserUtil;

public class ApiManager extends ApiClient {

    /**
     * ArrayList for apiClients
     */
    private static ArrayList<ApiClient> apiClients = new ArrayList<>();

    /**
     * Sets API token
     * @param token Api token
     */
    public static void setToken(String token) {
        System.out.println("Updated token " + token);
        for (ApiClient apiClient : apiClients) {
            apiClient.setApiKey(token);
        }
    }

    /**
     * Adds API Token
     * @param api ApiClient
     * @param preferences add token to SharedPreferences
     */
    public static void add(ApiClient api, SharedPreferences preferences) {
        api.setBasePath(Globals.API_BASEURL);
        apiClients.add(api);

        if (UserUtil.isLoggedIn(preferences)) {
            setToken(UserUtil.getToken(preferences));
        }
    }
}
