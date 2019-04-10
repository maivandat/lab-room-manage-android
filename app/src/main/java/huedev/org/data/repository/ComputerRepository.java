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
    public Single<ListComputerResponse> computersByRoom() {
        return mComputerRemoteDataSource.computersByRoom();
    }
}
