
package com.example.saleem.mostpopular.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium  implements Parcelable {
/*
    public static Medium create(String type, String subtype, String caption, String copyright, String approvedForSyndication, List<MediaMetadatum> mediaMetadata) {
        return new AutoValue_Medium(type, subtype, caption, copyright, approvedForSyndication, mediaMetadata);
    }
*/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Medium createFromParcel(Parcel in) {
            return new Medium(in);
        }

        public Medium[] newArray(int size) {
            return new Medium[size];
        }
    };

    public Medium(Parcel in) {
        this.type = in.readString();
        this.subtype = in.readString();
        this.caption = in.readString();
        this.copyright = in.readString();
        this.approvedForSyndication = in.readInt();
        //this.mediaMetadata = in.rea

        if (in.readByte() == 0x01) {
            this.mediaMetadata = new ArrayList<MediaMetadatum>();
            in.readList(this.mediaMetadata, MediaMetadatum.class.getClassLoader());
        } else {
            this.mediaMetadata = null;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.subtype);
        dest.writeString(this.caption);
        dest.writeString(this.copyright);
        dest.writeInt(this.approvedForSyndication);
        //dest.writeList(this.mediaMetadata);

        if (this.mediaMetadata == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(this.mediaMetadata);
        }

    }

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("approved_for_syndication")
    @Expose
    private Integer approvedForSyndication;
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadatum> mediaMetadata = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getApprovedForSyndication() {
        return approvedForSyndication;
    }

    public void setApprovedForSyndication(Integer approvedForSyndication) {
        this.approvedForSyndication = approvedForSyndication;
    }

    public List<MediaMetadatum> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadatum> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

}
