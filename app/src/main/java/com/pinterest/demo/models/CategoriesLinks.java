
package com.pinterest.demo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesLinks implements Parcelable
{

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("photos")
    @Expose
    private String photos;
    public final static Creator<CategoriesLinks> CREATOR = new Creator<CategoriesLinks>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CategoriesLinks createFromParcel(Parcel in) {
            return new CategoriesLinks(in);
        }

        public CategoriesLinks[] newArray(int size) {
            return (new CategoriesLinks[size]);
        }

    }
    ;

    protected CategoriesLinks(Parcel in) {
        this.self = ((String) in.readValue((String.class.getClassLoader())));
        this.photos = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CategoriesLinks() {
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(self);
        dest.writeValue(photos);
    }

    public int describeContents() {
        return  0;
    }

}
