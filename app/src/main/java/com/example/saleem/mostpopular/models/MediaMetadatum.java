
package com.example.saleem.mostpopular.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMetadatum   implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MediaMetadatum createFromParcel(Parcel in) {
            return new MediaMetadatum(in);
        }

        public MediaMetadatum[] newArray(int size) {
            return new MediaMetadatum[size];
        }
    };

    public MediaMetadatum(Parcel in) {
        this.url = in.readString();
        this.format = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.format);
        dest.writeInt(this.height);
        dest.writeInt(this.width);

    }
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}
