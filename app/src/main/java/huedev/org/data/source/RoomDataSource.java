package huedev.org.data.source;

import huedev.org.data.source.remote.response.ListRoomResponse;
import io.reactivex.Single;

public interface RoomDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListRoomResponse> rooms();
    }
}
