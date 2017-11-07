package com.example.borja.tasks.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Borja on 21/10/17.
 */

public class Task implements Parcelable {

    private int identifier = Integer.MIN_VALUE;
    private String title;
    private String message;
    private boolean done = false;

    public Task(int identifier, String title, String message, boolean done) {
        this.identifier = identifier;
        this.title = title;
        this.message = message;
        this.done = done;
    }

    public Task(String title, String message) {
        this.title = title;
        this.message = message;
        this.done = false;
    }

    public Task(Parcel parcel) { readFromParcel(parcel);}

    public Task(JSONObject json) throws JSONException {

        identifier = json.getInt("id");
        title = json.getString("title");
        message = json.getString("message");

        try {
            done = json.getBoolean("done");

        }catch (Exception e) {
            done = (json.getInt("done") == 1);
        }
    }


    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        if (identifier != Integer.MIN_VALUE) {
            json.put("id", identifier);
        }

        json.put("title", title);
        json.put("message", message);
        json.put("done", done);

        return json;

        /* Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(identifier));
        map.put("title", title);
        map.put("message", message);
        map.put("done", (done? "1":"0"));

        JSONObject json = new JSONObject(map);*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(identifier);
        parcel.writeString(title);
        parcel.writeString(message);
        boolean[] bools = new boolean[]{done};
        parcel.writeBooleanArray(bools);
    }

    private void readFromParcel(Parcel parcel) {
        identifier = parcel.readInt();
        title = parcel.readString();
        message = parcel.readString();
        boolean[] temp = new boolean[1];
        parcel.readBooleanArray(temp);
        done = temp[0];
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
