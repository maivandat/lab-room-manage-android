package huedev.org.data.source.remote.response.computer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import huedev.org.data.model.Computer;

public class ListComputerResponse {
    @SerializedName("data")
    @Expose
    public List<Computer> computersByRoom;
}
