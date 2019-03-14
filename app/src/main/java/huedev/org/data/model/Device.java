package huedev.org.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Device implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type_devices_id")
    @Expose
    private int typeDevicesId;
    @SerializedName("computers_id")
    @Expose
    private int computersId;

    protected Device(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.desc = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.typeDevicesId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.computersId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getTypeDevicesId() {
        return typeDevicesId;
    }

    public void setTypeDevicesId(int typeDevicesId) {
        this.typeDevicesId = typeDevicesId;
    }

    public int getComputersId() {
        return computersId;
    }

    public void setComputersId(int computersId) {
        this.computersId = computersId;
    }

    public final static Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return (new Device[size]);
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(desc);
        dest.writeValue(status);
        dest.writeValue(typeDevicesId);
        dest.writeValue(computersId);
    }

}