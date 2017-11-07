package com.example.borja.tasks.IO.operations;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.borja.tasks.IO.SessionManager;
import com.example.borja.tasks.IO.VolleySingleton;
import com.example.borja.tasks.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.borja.tasks.IO.NetworkConstants.*;

/**
 * Created by Borja on 21/10/17.
 */

/**
 * user: borjaigartua
 * pass: borjaigartua
 */

public class LoginOperation {

    public static void login(Context context, String username, String password, final OperationSuccessListener successListener, final OperationErrorListener errorListener) {
        String  REQUEST_TAG = "com.tasks.app.login";

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        JSONObject request = new JSONObject(map);

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, LOGIN_OPERATION, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    User user = new User(response);
                    if (successListener != null) {
                        successListener.success(user);
                    }

                } catch (JSONException e) {

                    if (errorListener != null) {
                        errorListener.error(e);
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorListener != null) {
                    errorListener.error(error);
                }
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                JSONObject headers = new JSONObject(response.headers);
                try {
                    SessionManager.getInstance().storeSailsSession(headers.get("set-cookie").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }
}
