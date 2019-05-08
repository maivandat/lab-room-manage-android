package huedev.org.data.source.remote.response.tag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.Tag;

public class CreateTagReponse {
    @SerializedName("data")
    @Expose
    public Tag tagItem;
}
