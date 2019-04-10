package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.DeviceDataSource;
import huedev.org.data.source.remote.api.ApiDevice;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class DeviceRemoteDataSource implements DeviceDataSource.RemoteDataSource {
    private static DeviceRemoteDataSource instance;
    private ApiDevice mapiDevice;

    public DeviceRemoteDataSource(ApiDevice tempDeviceRemoteInstance) {
        mapiDevice = tempDeviceRemoteInstance;
    }

    public static synchronized DeviceRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new DeviceRemoteDataSource(AppServiceClient.getTempDeviceRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListDeviceResponse> tempDevices() {
        return mapiDevice.tempDevices();
    }
}
