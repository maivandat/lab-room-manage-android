package huedev.org.data.source;

import huedev.org.data.source.remote.response.room.ListRoomResponse;
import io.reactivex.Single;

public interface RoomDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListRoomResponse> rooms();
    }
}
