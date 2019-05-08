
package huedev.org.data.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("devices_id")
    @Expose
    public final static Parcelable.Creator<Tag> CREATOR = new Creator<Tag>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return (new Tag[size]);
        }

    }
            ;
    private final static long serialVersionUID = 7902441415082741855L;

    protected Tag(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }

}
