package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRoom {
    @GET("rooms")
    Single<ListRoomReponse> rooms();

    @FormUrlEncoded
    @POST("rooms")
    Single<CreateRoomReponse> createRoom(@Field("name") String name,
                                         @Field("desc") String desc,
                                         @Field("status") String status);
}
