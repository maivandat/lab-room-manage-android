package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.ListUserResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiDevice {
    @GET("devices")
    Single<ListUserResponse> devices();
}
