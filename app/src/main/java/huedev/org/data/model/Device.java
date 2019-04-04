package huedev.org.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("typpe_devices_id")
    @Expose
    private String typpeDevicesId;
    @SerializedName("computers_id")
    @Expose
    private String computersId;
    public final static Parcelable.Creator<Device> CREATOR = new Creator<Device>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return (new Device[size]);
        }

    }
            ;

    protected Device(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.desc = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.typpeDevicesId = ((String) in.readValue((String.class.getClassLoader())));
        this.computersId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTyppeDevicesId() {
        return typpeDevicesId;
    }

    public void setTyppeDevicesId(String typpeDevicesId) {
        this.typpeDevicesId = typpeDevicesId;
    }

    public String getComputersId() {
        return computersId;
    }

    public void setComputersId(String computersId) {
        this.computersId = computersId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(desc);
        dest.writeValue(status);
        dest.writeValue(typpeDevicesId);
        dest.writeValue(computersId);
    }

    public int describeContents() {
        return 0;
    }

}