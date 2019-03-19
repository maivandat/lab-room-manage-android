package huedev.org.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import huedev.org.data.model.Room;

public class ListRoomResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public List<Room> roomList;
}
