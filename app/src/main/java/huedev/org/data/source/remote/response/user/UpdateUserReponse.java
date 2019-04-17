package huedev.org.data.source.remote.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.User;

public class UpdateUserReponse {
    @SerializedName("data")
    @Expose
    public User user;
}
