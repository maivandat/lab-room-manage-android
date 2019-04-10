package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.DeviceDataSource;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import io.reactivex.Single;

public class DeviceRepository implements DeviceDataSource.LocalDataSource, DeviceDataSource.RemoteDataSource {
    public static DeviceRepository instance;

    private DeviceRemoteDataSource deviceRemoteDataSource;

    private DeviceLocalDataSource deviceLocalDataSource;


    public DeviceRepository(DeviceRemoteDataSource deviceRemoteDataSource
            , DeviceLocalDataSource deviceLocalDataSource) {
        this.deviceRemoteDataSource = deviceRemoteDataSource;
        this.deviceLocalDataSource = deviceLocalDataSource;
    }

    public static synchronized DeviceRepository getInstance(@NonNull DeviceLocalDataSource deviceLocalDataSource,
                                                            @NonNull DeviceRemoteDataSource deviceRemoteDataSource){
        if(instance == null){
            instance = new DeviceRepository(deviceRemoteDataSource, deviceLocalDataSource);
        }
        return instance;
    }

    @Override
    public Single<ListDeviceResponse> tempDevices() {
        return deviceRemoteDataSource.tempDevices();
    }
}
