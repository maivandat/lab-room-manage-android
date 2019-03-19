package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.RoomDataSource;
import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.data.source.remote.response.ListRoomResponse;
import huedev.org.data.source.remote.response.ListUserResponse;
import io.reactivex.Single;

public class RoomRepository implements RoomDataSource.LocalDataSource, RoomDataSource.RemoteDataSource {
    private static RoomRepository instance;

    @NonNull
    private RoomLocalDataSource mRoomLocalDataSource;

    @NonNull
    private RoomRemoteDataSource mRoomRemoteDataSource;

    public RoomRepository(@NonNull RoomLocalDataSource roomLocalDataSource,
                           @NonNull RoomRemoteDataSource remoteDataSource){
        mRoomLocalDataSource = roomLocalDataSource;
        mRoomRemoteDataSource = remoteDataSource;
    }

    public static synchronized RoomRepository getInstance(@NonNull RoomLocalDataSource roomLocalDataSource,
                                                          @NonNull RoomRemoteDataSource roomRemoteDataSource){
        if(instance == null){
            instance = new RoomRepository(roomLocalDataSource, roomRemoteDataSource);
        }
        return instance;
    }

    @Override
    public Single<ListRoomResponse> rooms() {
        return mRoomRemoteDataSource.rooms();
    }
}
