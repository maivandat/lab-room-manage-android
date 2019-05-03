package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.computer.CreateCPTReponse;
import huedev.org.data.source.remote.response.computer.ListCPTResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiComputer {
    @GET("computers")
    Single<ListCPTResponse> computersByRoom ();

    @FormUrlEncoded
    @POST("computers")
    Single<CreateCPTReponse> computerItem(@Field("name") String name,
                                          @Field("desc") String desc,
                                          @Field("status") int status,
                                          @Field("rooms_id") int room_id);
}
