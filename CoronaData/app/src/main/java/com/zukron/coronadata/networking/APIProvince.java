package com.zukron.coronadata.networking;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zukron.coronadata.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/12/2020
 */
public class APIProvince {
    private Context context;
    private OnResponse onResponse;

    public APIProvince(Context context, OnResponse onResponse) {
        this.context = context;
        this.onResponse = onResponse;
    }

    public void getProvinceById(final int id) {
        String url = KawalCoronaAPI.province;

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        JSONObject attributesJson = json.getJSONObject("attributes");

                        String idFromServer = attributesJson.getString("Kode_Provi");
                        String idFromClient = String.valueOf(id);

                        if (idFromServer.equals(idFromClient)) {
                            Province province = new Province();
                            province.setConfirmed(attributesJson.getInt("Kasus_Posi"));
                            province.setRecovered(attributesJson.getInt("Kasus_Semb"));
                            province.setDeaths(attributesJson.getInt("Kasus_Meni"));

                            onResponse.provinceResponse(province);
                        }
                    }
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
        void provinceResponse(Province province);

        void errorResponse(VolleyError error);
    }
}
