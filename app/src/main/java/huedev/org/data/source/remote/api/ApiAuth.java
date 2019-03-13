package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.auth.LoginResponse;
import io.reactivex.Single;
import retrofit2.http.POST;

public interface ApiAuth {

    @POST("auth/login")
    Single<LoginResponse> login(String email, String password);
}
