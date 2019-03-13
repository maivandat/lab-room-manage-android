package huedev.org.data.source.remote.service;

import android.content.Context;

import huedev.org.data.source.remote.api.ApiAuth;
import huedev.org.utils.AppConstants;

public class AppServiceClient extends ServiceClient {

    private static ApiAuth mApiAuth;

    public static ApiAuth getLoginRemoteInstance(Context context) {
        if(mApiAuth == null){
            mApiAuth = createService(context, AppConstants.HOST_URL, ApiAuth.class);
        }
        return mApiAuth;
    }
}
