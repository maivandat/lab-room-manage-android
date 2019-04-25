package huedev.org.data.source.remote.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.User;
import io.reactivex.Single;

public class CreateUserReponse {
    @SerializedName("data")
    @Expose
    Single<User> userItem;
}
