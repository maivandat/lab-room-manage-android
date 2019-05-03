package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.ComputerDataSource;
import huedev.org.data.source.remote.api.ApiComputer;
import huedev.org.data.source.remote.response.computer.CreateCPTReponse;
import huedev.org.data.source.remote.response.computer.ListCPTResponse;
import huedev.org.data.source.remote.response.computer.UpdateCPTReponse;
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
    public Single<ListCPTResponse> computersByRoom() {
        return mApiComputer.computersByRoom();
    }

    @Override
    public Single<CreateCPTReponse> computerItem(String name, String desc,
                                                 int status, int room_id) {
        return mApiComputer.computerItem(name, desc, status, room_id);
    }

    @Override
    public Single<UpdateCPTReponse> computerItem(int id, String name, String desc, int status, int room_id) {
        return mApiComputer.computerItem(id, name, desc, status, room_id);
    }

    @Override
    public Single<Void> deleteItem(int id) {
        return mApiComputer.deleteItem(id);
    }

}
