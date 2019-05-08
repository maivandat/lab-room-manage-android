package huedev.org.data.source.remote.response.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.Device;

public class UpdateDeviceReponse {
    @SerializedName("data")
    @Expose
    public Device deviceItem;
}
