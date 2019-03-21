package huedev.org.data.source.remote.service;

import android.content.Context;

import huedev.org.data.source.remote.api.ApiAuth;
import huedev.org.data.source.remote.api.ApiComputer;
import huedev.org.data.source.remote.api.ApiDevice;
import huedev.org.data.source.remote.api.ApiRoom;
import huedev.org.data.source.remote.api.ApiUser;
import huedev.org.utils.AppConstants;

public class AppServiceClient extends ServiceClient {

    private static ApiAuth mApiAuth;
    private static ApiUser mApiUser;
    private static ApiDevice mApiDevice;
    private static ApiRoom mApiRoom;
    private static ApiComputer mApiComputer;

    public static ApiAuth getLoginRemoteInstance(Context context) {
        if(mApiAuth == null){
            mApiAuth = createService(context, AppConstants.HOST_URL, ApiAuth.class);
        }
        return mApiAuth;
    }

    public static ApiUser getUserRemoteInstance(Context context) {
        if(mApiUser == null){
            mApiUser = createService(context, AppConstants.HOST_URL, ApiUser.class);
        }
        return mApiUser;
    }

    public static ApiDevice getDeviceRemoteInstance(Context context){
        if (mApiDevice == null){
            mApiDevice = createService(context, AppConstants.HOST_URL, ApiDevice.class);
        }
        return mApiDevice;
    }

    public static ApiRoom getRoomRemoteInstance(Context context){
        if (mApiRoom == null){
            mApiRoom = createService(context, AppConstants.HOST_URL, ApiRoom.class);
        }
        return mApiRoom;
    }

    public static ApiComputer getComputerRemoteInstance(Context context){
        if (mApiComputer == null){
            mApiComputer = createService(context, AppConstants.HOST_URL, ApiComputer.class);
        }
        return mApiComputer;
    }
}
