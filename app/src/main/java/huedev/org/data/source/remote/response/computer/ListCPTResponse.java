package huedev.org.data.source.remote.response.computer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import huedev.org.data.model.Computer;
import huedev.org.data.source.remote.response.BaseResponse;

public class ListCPTResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<Computer> computersByRoom;
}
