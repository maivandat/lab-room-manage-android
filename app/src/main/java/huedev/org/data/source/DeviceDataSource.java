package huedev.org.data.source;

import huedev.org.data.source.remote.response.device.CreateDeviceReponse;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import io.reactivex.Single;

public interface DeviceDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListDeviceResponse> tempDevices();
        Single<CreateDeviceReponse> createDevice(String name, String desc, int status, int id_type_device, int id_computer);
    }
}
