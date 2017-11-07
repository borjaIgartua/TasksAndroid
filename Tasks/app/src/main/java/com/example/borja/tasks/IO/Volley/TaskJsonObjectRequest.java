package com.example.borja.tasks.IO.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.borja.tasks.IO.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borja on 22/10/17.
 */

public class TaskJsonObjectRequest extends JsonObjectRequest {

    public TaskJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method,url,jsonRequest,listener,errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String sailsCookie =  SessionManager.getInstance().getSailsSession();
        headers.put("cookie", sailsCookie.toString());
        return headers;
    }
}
