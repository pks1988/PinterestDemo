
package com.pinterest.demo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_image")
    @Expose
    private ProfileImage profileImage;
    @SerializedName("userLinks")
    @Expose
    private UserLinks userLinks;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
            "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
    ;

    protected User(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.profileImage = ((ProfileImage) in.readValue((ProfileImage.class.getClassLoader())));
        this.userLinks = ((UserLinks) in.readValue((UserLinks.class.getClassLoader())));
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public UserLinks getUserLinks() {
        return userLinks;
    }

    public void setUserLinks(UserLinks userLinks) {
        this.userLinks = userLinks;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(username);
        dest.writeValue(name);
        dest.writeValue(profileImage);
        dest.writeValue(userLinks);
    }

    public int describeContents() {
        return  0;
    }

}
