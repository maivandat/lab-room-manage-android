package huedev.org.utils.helpers;

import android.content.Context;

public class StringHelper {
    public static String dateToString(){
        return String.format("time %s", "");
    }

    public static String getStringResourceByName(String aString, Context mContext) {
        String packageName = mContext.getPackageName();
        int resId = mContext.getResources().getIdentifier(aString, "string", packageName);
        return mContext.getString(resId);
    }
}
