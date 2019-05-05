package huedev.org.data.source;

import huedev.org.data.source.remote.response.type_device.ListTDReponse;
import io.reactivex.Single;

public interface TypeDeviceDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource {
        Single<ListTDReponse> listTD();
    }
}
