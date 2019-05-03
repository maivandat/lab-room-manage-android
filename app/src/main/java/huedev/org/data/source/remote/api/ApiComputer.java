package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.computer.CreateCPTReponse;
import huedev.org.data.source.remote.response.computer.ListCPTResponse;
import huedev.org.data.source.remote.response.computer.UpdateCPTReponse;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiComputer {
    @GET("computers")
    Single<ListCPTResponse> computersByRoom ();

    @FormUrlEncoded
    @POST("computers")
    Single<CreateCPTReponse> computerItem(@Field("name") String name,
                                          @Field("desc") String desc,
                                          @Field("status") int status,
                                          @Field("rooms_id") int room_id);
    @FormUrlEncoded
    @PUT("computers/{id}")
    Single<UpdateCPTReponse> computerItem(@Path("id") int id,
                                          @Field("name") String name,
                                          @Field("desc") String desc,
                                          @Field("status") int status,
                                          @Field("rooms_id") int room_id);

    @DELETE("computers/{id}")
    Single<Void> deleteItem(@Path("id") int id);
}
