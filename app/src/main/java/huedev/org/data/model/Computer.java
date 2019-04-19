package huedev.org.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Computer extends BaseModel implements Parcelable
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
    @SerializedName("rooms_id")
    @Expose
    private String roomId;
    public final static Parcelable.Creator<Computer> CREATOR = new Creator<Computer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Computer createFromParcel(Parcel in) {
            return new Computer(in);
        }

        public Computer[] newArray(int size) {
            return (new Computer[size]);
        }

    }
            ;

    protected Computer(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.desc = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.roomId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Computer() {
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(desc);
        dest.writeValue(status);
        dest.writeValue(roomId);
    }

    public int describeContents() {
        return 0;
    }

}
