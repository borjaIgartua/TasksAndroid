package com.example.borja.tasks.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Borja on 21/10/17.
 */

public class User implements Parcelable {
    private int identifier;
    private String username;
    private String password;

    public User(int identifier, String username, String password) {
        this.identifier = identifier;
        this.username = username;
        this.password = password;
    }

    public User(JSONObject jsonObject) throws JSONException {
        this.identifier = jsonObject.getInt("id");
        this.username = jsonObject.getString("username");
        this.password = jsonObject.getString("password");
    }

    public User(String jsonString) throws JSONException {
        this(new JSONObject(jsonString));
    }

    public User(Parcel parcel) { readFromParcel(parcel);}

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(identifier);
        parcel.writeString(username);
        parcel.writeString(password);
    }

    private void readFromParcel(Parcel parcel) {
        identifier = parcel.readInt();
        username = parcel.readString();
        password = parcel.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
