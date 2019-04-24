package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import huedev.org.data.source.remote.response.room.UpdateRoomReponse;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRoom {
    @GET("rooms")
    Single<ListRoomReponse> rooms();

    @FormUrlEncoded
    @POST("rooms")
    Single<CreateRoomReponse> createRoom(@Field("name") String name,
                                         @Field("desc") String desc,
                                         @Field("status") String status);

    @FormUrlEncoded
    @PUT("rooms/{id}")
    Single<UpdateRoomReponse> updateRoom(@Path("id") String id,
                                         @Field("name") String name,
                                         @Field("desc") String desc,
                                         @Field("status") String status);

    @DELETE("rooms/{id}")
    Single<Void> delete(@Path("id") String id);
}
