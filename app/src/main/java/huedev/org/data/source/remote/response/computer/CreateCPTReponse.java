package huedev.org.data.source.remote.response.computer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.Computer;

public class CreateCPTReponse {
    @SerializedName("data")
    @Expose
    public Computer computerItem;
}
