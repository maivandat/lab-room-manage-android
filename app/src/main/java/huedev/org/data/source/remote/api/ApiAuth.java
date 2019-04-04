package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.auth.LoginResponse;
import io.reactivex.Single;
import retrofit2.http.POST;

public interface ApiAuth {

    @POST("api/v1/login")
    Single<LoginResponse> login(String username, String password);
}
