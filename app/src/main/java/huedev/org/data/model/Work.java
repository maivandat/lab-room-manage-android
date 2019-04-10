package huedev.org.data.model;

import java.io.Serializable;
import java.util.jar.Attributes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Work implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    private final static long serialVersionUID = 7036618553595109903L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Work(String name, String time, String date) {
        this.name = name;
        this.time = time;
        this.date = date;
    }

    public Work() {

    }
}
