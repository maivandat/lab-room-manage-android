package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.auth.LoginResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiAuth {
    @FormUrlEncoded
    @POST("v1/login")
    Single<LoginResponse> login(@Field("username") String username,@Field("password")String password);
}
