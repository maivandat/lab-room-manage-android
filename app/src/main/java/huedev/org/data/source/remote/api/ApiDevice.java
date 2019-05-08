package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.device.CreateDeviceReponse;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.data.source.remote.response.device.UpdateDeviceReponse;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDevice {
    @GET("devices")
    Single<ListDeviceResponse> tempDevices();

    @FormUrlEncoded
    @POST("devices")
    Single<CreateDeviceReponse> createDevice(@Field("name") String name,
                                             @Field("desc") String desc,
                                             @Field("status") int status,
                                             @Field("type_devices_id") int id_type_device,
                                             @Field("computers_id") int id_computer);

    @FormUrlEncoded
    @PUT("devices{id}")
    Single<UpdateDeviceReponse> updateDevice(@Path("id") int id,
                                             @Field("name") String name,
                                             @Field("name") String desc,
                                             @Field("status") int status,
                                             @Field("type_devices_id") int id_type_device,
                                             @Field("computers_id") int id_computer);

    @DELETE("devices{id}")
    Single<Void> deleteDevice(@Path("id") int id);
}
