package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.ComputerDataSource;
import huedev.org.data.source.remote.response.computer.CreateCPTReponse;
import huedev.org.data.source.remote.response.computer.ListCPTResponse;
import huedev.org.data.source.remote.response.computer.UpdateCPTReponse;
import io.reactivex.Single;

public class ComputerRepository implements ComputerDataSource.LocalDataSource, ComputerDataSource.RemoteDataSource {
    private static ComputerRepository instance;

    @NonNull
    private ComputerDataSource.LocalDataSource mComputerLocalDataSource;

    @NonNull
    private ComputerDataSource.RemoteDataSource mComputerRemoteDataSource;

    public ComputerRepository(@NonNull ComputerDataSource.LocalDataSource computerLocalDataSource,
                          @NonNull ComputerDataSource.RemoteDataSource computerRemoteDataSource){
        mComputerLocalDataSource = computerLocalDataSource;
        mComputerRemoteDataSource = computerRemoteDataSource;
    }

    public static synchronized ComputerRepository getInstance(@NonNull ComputerDataSource.LocalDataSource computerLocalDataSource,
                                                              @NonNull ComputerDataSource.RemoteDataSource computerRemoteDataSource){
        if(instance == null){
            instance = new ComputerRepository(computerLocalDataSource, computerRemoteDataSource);
        }
        return instance;
    }

    @Override
    public Single<ListCPTResponse> computersByRoom() {
        return mComputerRemoteDataSource.computersByRoom();
    }

    @Override
    public Single<CreateCPTReponse> computerItem(String name, String desc,
                                                 int status, int room_id) {
        return mComputerRemoteDataSource.computerItem(name, desc, status, room_id);
    }

    @Override
    public Single<UpdateCPTReponse> computerItem(int id, String name, String desc, int status, int room_id) {
        return mComputerRemoteDataSource.computerItem(id, name, desc, status, room_id);
    }

    @Override
    public Single<Void> deleteItem(int id) {
        return mComputerRemoteDataSource.deleteItem(id);
    }
}
