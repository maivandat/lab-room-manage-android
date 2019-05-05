package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.type_device.ListTDReponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiTypeDevice {
    @GET("typedevices")
    Single<ListTDReponse> listTD();
}
