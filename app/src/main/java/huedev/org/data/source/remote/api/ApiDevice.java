package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiDevice {
    @GET("devices")
    Single<ListDeviceResponse> tempDevices();
}
