package huedev.org.data.source.remote.response.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.User;

public class RegisterResponse {
    @SerializedName("data")
    @Expose
    public User itemUser;
}
