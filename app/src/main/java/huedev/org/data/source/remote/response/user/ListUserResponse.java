package huedev.org.data.source.remote.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import huedev.org.data.model.User;
import huedev.org.data.source.remote.response.BaseResponse;

public class ListUserResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public List<User> userList;
}
