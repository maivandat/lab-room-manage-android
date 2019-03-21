package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.room.ListRoomResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiRoom {
    @GET("rooms")
    Single<ListRoomResponse> rooms();
}
