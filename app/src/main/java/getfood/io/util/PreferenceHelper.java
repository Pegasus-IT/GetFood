package getfood.io.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import getfood.io.data.local.Globals;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceHelper {

    public static void save(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, encrypt(value)).apply();
    }

    public static void save(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value).apply();
    }

    public static String read(Context context, String key, String defaultReturn) {
        SharedPreferences preferences = getPreferences(context);
        String passEncrypted = preferences.getString(key, encrypt(defaultReturn));
        return decrypt(passEncrypted);
    }

    public static Boolean read(Context context, String key, Boolean defaultReturn) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(key, defaultReturn);
    }


    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.edit();
    }

    private static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
