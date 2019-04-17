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

    public void putIdUser(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.ID_USER, value);
        editor.commit();
    }

    public String getIdUser(){
        return preferences.getString(AppConstants.ID_USER,AppConstants.ID_USER_DEFAULT);
    }

    public void putNameUser(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.ID_NAME, value);
        editor.commit();
    }

    public String getNameUser(){
        return preferences.getString(AppConstants.ID_NAME,AppConstants.NAME_DEFAULT);
    }

    public void putUserNameUser(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.ID_USERNAME, value);
        editor.commit();
    }

    public String getUserNameUser(){
        return preferences.getString(AppConstants.ID_USERNAME,AppConstants.USERNAME_DEFAULT);
    }

    public void putPasswordUser(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.ID_PASSWORD, value);
        editor.commit();
    }

    public String getPasswordUser(){
        return preferences.getString(AppConstants.ID_PASSWORD,AppConstants.PASSWORD_DEFAULT);
    }

    public void putEmailUser(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.ID_EMAIL, value);
        editor.commit();
    }

    public String getEmailUser(){
        return preferences.getString(AppConstants.ID_EMAIL,AppConstants.EMAIL_DEFAULT);
    }

    public void putRole(int value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AppConstants.ID_ROLE, value);
        editor.commit();
    }

    public int getRole(){
        return preferences.getInt(AppConstants.ID_ROLE,AppConstants.ROLE_DEFAULT);
    }



}
