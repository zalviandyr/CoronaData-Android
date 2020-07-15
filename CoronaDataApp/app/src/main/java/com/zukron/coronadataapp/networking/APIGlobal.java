package com.zukron.coronadataapp.networking;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zukron.coronadataapp.model.Global;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/12/2020
 */
public class APIGlobal {
    private Context context;
    private OnResponse onResponse;

    public APIGlobal(Context context, OnResponse onResponse) {
        this.context = context;
        this.onResponse = onResponse;
    }

    public void getGlobal() {
        String url = MathdroAPI.BaseUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject confirmedJson = json.getJSONObject("confirmed");
                    JSONObject recoveredJson = json.getJSONObject("recovered");
                    JSONObject deathsJson = json.getJSONObject("deaths");
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                    LocalDateTime lastUpdate = LocalDateTime.parse(json.getString("lastUpdate"), dateTimeFormatter);

                    Global global = new Global();
                    global.setConfirmed(confirmedJson.getInt("value"));
                    global.setRecovered(recoveredJson.getInt("value"));
                    global.setDeaths(deathsJson.getInt("value"));
                    global.setLastUpdate(lastUpdate);

                    onResponse.globalResponse(global);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResponse.errorResponse(error);
            }
        });

        execute(stringRequest);
    }

    private void execute(StringRequest stringRequest) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public interface OnResponse {
        void globalResponse(Global global);

        void errorResponse(VolleyError error);
    }
}
