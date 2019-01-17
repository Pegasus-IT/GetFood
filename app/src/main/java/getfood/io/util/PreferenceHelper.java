package getfood.io.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceHelper {

    public static void save(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("main_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, encrypt(value));
        editor.apply();
    }

    public static String read(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("main_preferences", MODE_PRIVATE);
        String passEncrypted = preferences.getString(key, encrypt("default"));
        return decrypt(passEncrypted);
    }

    private static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
