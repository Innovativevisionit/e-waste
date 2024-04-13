package com.example.wastetowealth.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String PREFS_NAME = "MyPrefs";
    private static MySharedPreferences instance;
    private SharedPreferences sharedPreferences;

    private MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized MySharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new MySharedPreferences(context.getApplicationContext());
        }
        return instance;
    }

    // Method to save a string value to shared preferences
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Method to retrieve a string value from shared preferences
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

}
