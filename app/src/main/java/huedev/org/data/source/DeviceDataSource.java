package huedev.org.data.source;

import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import io.reactivex.Single;

public interface DeviceDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListDeviceResponse> devices();
    }
}
