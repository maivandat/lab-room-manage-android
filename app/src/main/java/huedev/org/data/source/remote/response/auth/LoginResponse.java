package huedev.org.data.source.remote.response.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.Login;
import huedev.org.data.model.User;
import huedev.org.data.source.remote.response.BaseResponse;

public class LoginResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public Login data;
}
