package huedev.org.data.source.remote.api;

import huedev.org.data.model.User;
import huedev.org.data.source.remote.response.auth.LoginResponse;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUser {
    @GET("users")
    Single<ListUserResponse> users();

    @FormUrlEncoded
    @PUT("users/{id}")
    Single<UpdateUserReponse> update(@Path("id") String id,
                                     @Field("username") String username,
                                     @Field("password") String password,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("role") int role);
}
