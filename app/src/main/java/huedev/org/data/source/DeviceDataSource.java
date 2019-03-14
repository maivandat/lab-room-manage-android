package huedev.org.data.source;

import huedev.org.data.source.remote.response.ListDeviceResponse;
import huedev.org.data.source.remote.response.ListUserResponse;
import io.reactivex.Single;

public interface DeviceDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListDeviceResponse> users();
    }
}
