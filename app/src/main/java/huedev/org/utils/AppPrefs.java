package huedev.org.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {
    private static AppPrefs instance;
    private SharedPreferences preferences;

    private AppPrefs(Context context){
        preferences = context.getApplicationContext().getSharedPreferences(AppConstants.APP_PREFS_NAME, Context.MODE_PRIVATE);
    }
    public static AppPrefs getInstance(Context context){
        if(instance == null){
            instance = new AppPrefs(context);
        }
        return instance;
    }

    public void putApiToken(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.API_TOKEN, value);
        editor.commit();
    }

    public String getApiToken(){
        return preferences.getString(AppConstants.API_TOKEN,AppConstants.API_TOKEN_DEFAULT);
    }

}
