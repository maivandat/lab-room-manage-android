package huedev.org.data.source;

import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import io.reactivex.Single;

public interface RoomDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListRoomReponse> rooms();
        Single<CreateRoomReponse> createRoom(String name, String desc, String status);
    }
}
