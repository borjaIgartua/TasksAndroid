package com.example.borja.tasks.IO;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Borja on 22/10/17.
 */
public class SessionManager {

    private static volatile SessionManager singleton;

    private String session;

    //private constructor.
    private SessionManager(){
        //Prevent form the reflection api.
        if (singleton != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SessionManager getInstance() {
        if (singleton == null) { //if there is no instance available... create new one
            synchronized (SessionManager.class) {
                if (singleton == null)
                    singleton = new SessionManager();
            }
        }

        return singleton;
    }



    public void storeSailsSession(String session){
        this.session = session;

    }
    public String getSailsSession(){
        return session;
    }

}