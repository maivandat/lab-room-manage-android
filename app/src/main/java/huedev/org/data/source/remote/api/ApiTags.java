package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.tag.CreateTagReponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiTags {
    @FormUrlEncoded
    @POST("tags")
    Single<CreateTagReponse> createTags(@Field("value") String value,
                                        @Field("devices_id") int id_device);

}
