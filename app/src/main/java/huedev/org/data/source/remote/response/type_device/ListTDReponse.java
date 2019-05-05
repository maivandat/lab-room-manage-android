package huedev.org.data.source.remote.response.type_device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import huedev.org.data.model.TypeDevice;

public class ListTDReponse {
    @SerializedName("data")
    @Expose
    public List<TypeDevice> listTD;

}
