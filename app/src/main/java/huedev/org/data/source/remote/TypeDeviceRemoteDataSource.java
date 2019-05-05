package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.TypeDeviceDataSource;
import huedev.org.data.source.remote.api.ApiTypeDevice;
import huedev.org.data.source.remote.response.type_device.ListTDReponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class TypeDeviceRemoteDataSource implements TypeDeviceDataSource.RemoteDataSource {
    private static TypeDeviceRemoteDataSource instance;
    private ApiTypeDevice mApiTypeDevice;

    public TypeDeviceRemoteDataSource(ApiTypeDevice apiTypeDevice){
        mApiTypeDevice = apiTypeDevice;
    }

    public static synchronized TypeDeviceRemoteDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new TypeDeviceRemoteDataSource(AppServiceClient.getTypeDeviceRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListTDReponse> listTD() {
        return mApiTypeDevice.listTD();
    }
}
