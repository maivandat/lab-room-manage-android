package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.ComputerDataSource;
import huedev.org.data.source.remote.api.ApiComputer;
import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class ComputerRemoteDataSource implements ComputerDataSource.RemoteDataSource {
    private static ComputerRemoteDataSource instance;
    private ApiComputer mApiComputer;

    public ComputerRemoteDataSource(ApiComputer apiComputer){
        mApiComputer = apiComputer;
    }

    public static synchronized ComputerRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new ComputerRemoteDataSource(AppServiceClient.getComputerRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListComputerResponse> computersByRoom(int roomId) {
        return mApiComputer.computersByRoom(roomId);
    }
}
