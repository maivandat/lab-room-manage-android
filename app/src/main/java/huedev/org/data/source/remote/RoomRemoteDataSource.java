package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.RoomDataSource;
import huedev.org.data.source.remote.api.ApiRoom;
import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class RoomRemoteDataSource implements RoomDataSource.RemoteDataSource {
    private static RoomRemoteDataSource instance;
    private ApiRoom mApiRoom;

    public RoomRemoteDataSource(ApiRoom apiRoom){
        mApiRoom = apiRoom;
    }

    public static synchronized RoomRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new RoomRemoteDataSource(AppServiceClient.getRoomRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListRoomReponse> rooms() {
        return mApiRoom.rooms();
    }

    @Override
    public Single<CreateRoomReponse> createRoom(String name, String desc, String status) {
        return mApiRoom.createRoom(name, desc, status);
    }
}
