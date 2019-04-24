package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.RoomDataSource;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import huedev.org.data.source.remote.response.room.UpdateRoomReponse;
import io.reactivex.Single;

public class RoomRepository implements RoomDataSource.LocalDataSource, RoomDataSource.RemoteDataSource {
    private static RoomRepository instance;

    @NonNull
    private RoomDataSource.LocalDataSource mRoomLocalDataSource;

    @NonNull
    private RoomDataSource.RemoteDataSource mRoomRemoteDataSource;

    public RoomRepository(@NonNull RoomDataSource.LocalDataSource roomLocalDataSource,
                           @NonNull RoomDataSource.RemoteDataSource remoteDataSource){
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
    public Single<ListRoomReponse> rooms() {
        return mRoomRemoteDataSource.rooms();
    }

    @Override
    public Single<CreateRoomReponse> createRoom(String name, String desc, String status) {
        return mRoomRemoteDataSource.createRoom(name, desc, status);
    }

    @Override
    public Single<UpdateRoomReponse> updateRoom(String id, String name, String desc, String status) {
        return mRoomRemoteDataSource.updateRoom(id, name, desc, status);
    }

    @Override
    public Single<Void> deleteRoom(String id) {
        return mRoomRemoteDataSource.deleteRoom(id);
    }


}
