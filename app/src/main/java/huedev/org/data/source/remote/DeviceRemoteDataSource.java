package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.DeviceDataSource;
import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.remote.api.ApiDevice;
import huedev.org.data.source.remote.api.ApiUser;
import huedev.org.data.source.remote.response.ListDeviceResponse;
import huedev.org.data.source.remote.response.ListUserResponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class DeviceRemoteDataSource implements DeviceDataSource.RemoteDataSource {
    private static DeviceRemoteDataSource instance;
    private ApiDevice mApiDevice;

    public DeviceRemoteDataSource(ApiDevice apiDevice){
        mApiDevice = apiDevice;
    }

    public static synchronized DeviceRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new DeviceRemoteDataSource(AppServiceClient.getDeviceRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListDeviceResponse> devices() {
        return mApiDevice.devices();
    }
}
