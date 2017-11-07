package com.example.borja.tasks.IO.operations;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.borja.tasks.IO.Volley.TaskJsonArrayRequest;
import com.example.borja.tasks.IO.Volley.TaskJsonObjectRequest;
import com.example.borja.tasks.IO.VolleySingleton;
import com.example.borja.tasks.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.borja.tasks.IO.NetworkConstants.*;

/**
 * Created by Borja on 21/10/17.
 */

public class TaskOperation {

    public static void retrieveTasks(Context context, final OperationSuccessListener successListener, final OperationErrorListener errorListener) {
        String REQUEST_TAG = "com.tasks.app.retrieve.tasks";

        TaskJsonArrayRequest jsonObjectReq = new TaskJsonArrayRequest(Request.Method.GET, RETRIEVE_TASKS_OPERATION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("TASKS", response.toString());
                try {

                    List<Task> tasks = new ArrayList<Task>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = response.getJSONObject(i);
                        Task task = new Task(json);
                        tasks.add(task);
                    }

                    successListener.success(tasks);

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
        });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public static void insertTask(Context context, Task task, final OperationSuccessListener successListener, final OperationErrorListener errorListener) {
        String REQUEST_TAG = "com.tasks.app.insert.tasks";

        try {

            TaskJsonObjectRequest jsonObjectReq = new TaskJsonObjectRequest(Request.Method.POST, INSERT_TASK_OPERATION, task.toJSONObject(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        Task task = new Task(response);
                        if (successListener != null) {
                            successListener.success(task);
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
            });
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

        } catch (JSONException e) {
            errorListener.error(e);
        }
    }

    public static void deleteTask(Context context, Task task, final OperationSuccessListener successListener, final OperationErrorListener errorListener) {
        String REQUEST_TAG = "com.tasks.app.delete.tasks";

        String url = DELETE_TASK_OPERATION + "/" + task.getIdentifier();
        TaskJsonObjectRequest jsonObjectReq = new TaskJsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                //TODO: test this
                if (successListener != null) {
                    successListener.success(null);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorListener != null) {
                    errorListener.error(error);
                }
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public static void updateTask(Context context, Task task, final OperationSuccessListener successListener, final OperationErrorListener errorListener) {
        String REQUEST_TAG = "com.tasks.app.update.tasks";

        try {
            TaskJsonObjectRequest jsonObjectReq = new TaskJsonObjectRequest(Request.Method.PUT, UPDATE_TASK_OPERATION, task.toJSONObject(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        Task task = new Task(response);
                        if (successListener != null) {
                            successListener.success(task);
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
            });
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

        } catch (JSONException e) {
            errorListener.error(e);
        }

    }
}