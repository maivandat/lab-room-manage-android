package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.DeviceDataSource;
import huedev.org.data.source.remote.api.ApiDevice;
import huedev.org.data.source.remote.response.device.CreateDeviceReponse;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.data.source.remote.response.device.UpdateDeviceReponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class DeviceRemoteDataSource implements DeviceDataSource.RemoteDataSource {
    private static DeviceRemoteDataSource instance;
    private ApiDevice mApiDevice;

    public DeviceRemoteDataSource(ApiDevice tempDeviceRemoteInstance) {
        mApiDevice = tempDeviceRemoteInstance;
    }

    public static synchronized DeviceRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new DeviceRemoteDataSource(AppServiceClient.getTempDeviceRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListDeviceResponse> tempDevices() {
        return mApiDevice.tempDevices();
    }

    @Override
    public Single<CreateDeviceReponse> createDevice(String name, String desc,
                                                    int status, int id_type_device,
                                                    int id_computer) {
        return mApiDevice.createDevice(name, desc, status, id_type_device, id_computer);
    }

    @Override
    public Single<UpdateDeviceReponse> updateDevice(int id, String name,
                                                    String desc, int status,
                                                    int id_type_device, int id_computer) {
        return mApiDevice.updateDevice(id, name, desc, status, id_type_device, id_computer);
    }

    @Override
    public Single<Void> deleteDevice(int id) {
        return mApiDevice.deleteDevice(id);
    }
}
