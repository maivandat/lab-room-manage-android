package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.user.ListUserResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiUser {
    @GET("users")
    Single<ListUserResponse> users();
}
