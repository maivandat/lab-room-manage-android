package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.ComputerDataSource;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import io.reactivex.Single;

public class ComputerRepository implements ComputerDataSource.LocalDataSource, ComputerDataSource.RemoteDataSource {
    private static ComputerRepository instance;

    @NonNull
    private ComputerLocalDataSource mComputerLocalDataSource;

    @NonNull
    private ComputerRemoteDataSource mComputerRemoteDataSource;

    public ComputerRepository(@NonNull ComputerLocalDataSource computerLocalDataSource,
                          @NonNull ComputerRemoteDataSource computerRemoteDataSource){
        mComputerLocalDataSource = computerLocalDataSource;
        mComputerRemoteDataSource = computerRemoteDataSource;
    }

    public static synchronized ComputerRepository getInstance(@NonNull ComputerLocalDataSource computerLocalDataSource,
                                                          @NonNull ComputerRemoteDataSource computerRemoteDataSource){
        if(instance == null){
            instance = new ComputerRepository(computerLocalDataSource, computerRemoteDataSource);
        }
        return instance;
    }

    @Override
    public Single<ListComputerResponse> computersByRoom(int id) {
        return mComputerRemoteDataSource.computersByRoom(id);
    }
}
