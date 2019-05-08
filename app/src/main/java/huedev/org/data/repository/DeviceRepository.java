package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.DeviceDataSource;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.data.source.remote.response.device.CreateDeviceReponse;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.data.source.remote.response.device.UpdateDeviceReponse;
import io.reactivex.Single;

public class DeviceRepository implements
        DeviceDataSource.LocalDataSource,
        DeviceDataSource.RemoteDataSource {
    public static DeviceRepository instance;

    private DeviceRemoteDataSource deviceRemoteDataSource;

    private DeviceLocalDataSource deviceLocalDataSource;


    public DeviceRepository(DeviceRemoteDataSource deviceRemoteDataSource
            , DeviceLocalDataSource deviceLocalDataSource) {
        this.deviceRemoteDataSource = deviceRemoteDataSource;
        this.deviceLocalDataSource = deviceLocalDataSource;
    }

    public static synchronized DeviceRepository getInstance(
            @NonNull DeviceLocalDataSource deviceLocalDataSource,
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

    @Override
    public Single<CreateDeviceReponse> createDevice(String name, String desc,
                                                    int status, int id_type_device,
                                                    int id_computer) {
        return deviceRemoteDataSource.createDevice(name, desc, status, id_type_device, id_computer);
    }

    @Override
    public Single<UpdateDeviceReponse> updateDevice(int id, String name,
                                                    String desc, int status,
                                                    int id_type_device, int id_computer) {
        return deviceRemoteDataSource.updateDevice(
                id, name, desc,
                status, id_type_device, id_computer);
    }

    @Override
    public Single<Void> deleteDevice(int id) {
        return deviceRemoteDataSource.deleteDevice(id);
    }
}
