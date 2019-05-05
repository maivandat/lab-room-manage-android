package huedev.org.data.repository;

import huedev.org.data.source.TypeDeviceDataSource;
import huedev.org.data.source.local.TypeDeviceLocalDataSource;
import huedev.org.data.source.remote.TypeDeviceRemoteDataSource;
import huedev.org.data.source.remote.response.type_device.ListTDReponse;
import io.reactivex.Single;
import android.support.annotation.NonNull;

public class TypeDeviceRepository implements TypeDeviceDataSource.LocalDataSource,
        TypeDeviceDataSource.RemoteDataSource {
    private static TypeDeviceRepository instance;

    @NonNull
    private TypeDeviceLocalDataSource mTypeDeviceLocalDataSource;
    @NonNull
    private TypeDeviceRemoteDataSource mTypeDeviceRemoteDataSource;

    public TypeDeviceRepository (@NonNull TypeDeviceLocalDataSource typeDeviceLocalDataSource,
                                 @NonNull TypeDeviceRemoteDataSource typeDeviceRemoteDataSource) {
        mTypeDeviceLocalDataSource = typeDeviceLocalDataSource;
        mTypeDeviceRemoteDataSource = typeDeviceRemoteDataSource;
    }

    public static synchronized TypeDeviceRepository getInstance(
            @NonNull TypeDeviceLocalDataSource typeDeviceLocalDataSource,
            @NonNull TypeDeviceRemoteDataSource typeDeviceRemoteDataSource){
        if(instance == null){
            instance = new TypeDeviceRepository(typeDeviceLocalDataSource, typeDeviceRemoteDataSource);
        }
        return instance;
    }
    @Override
    public Single<ListTDReponse> listTD() {
        return mTypeDeviceRemoteDataSource.listTD();
    }
}
