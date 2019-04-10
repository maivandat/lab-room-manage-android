package huedev.org.data.source.remote.response.room;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import huedev.org.data.model.Room;
import huedev.org.data.source.remote.response.BaseResponse;

public class  ListRoomResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public List<Room> roomList;

}
