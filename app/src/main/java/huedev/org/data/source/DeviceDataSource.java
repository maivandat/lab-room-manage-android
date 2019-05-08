package huedev.org.data.source;

import huedev.org.data.source.remote.response.device.CreateDeviceReponse;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.data.source.remote.response.device.UpdateDeviceReponse;
import io.reactivex.Single;

public interface DeviceDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListDeviceResponse> tempDevices();
        Single<CreateDeviceReponse> createDevice(String name, String desc,
                                                 int status, int id_type_device,
                                                 int id_computer);
        Single<UpdateDeviceReponse> updateDevice(int id, String name,
                                                 String desc, int status,
                                                 int id_type_device, int id_computer);
        Single<Void> deleteDevice(int id);
    }
}
