package com.zukron.coronadata.networking;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zukron.coronadata.model.Country;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.MessageFormat;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/10/2020
 */
public class APICountry {
    private Context context;
    private OnResponse onResponse;

    public APICountry(Context context, OnResponse onResponse) {
        this.context = context;
        this.onResponse = onResponse;
    }

    public void getCountryByIso(String iso) {
        String url = MessageFormat.format(MathdroAPI.CountryByIso, iso);
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

                    Country country = new Country();
                    country.setIso2("ID");
                    country.setName("Indonesia");
                    country.setConfirmed(confirmedJson.getInt("value"));
                    country.setRecovered(recoveredJson.getInt("value"));
                    country.setDeaths(deathsJson.getInt("value"));
                    country.setLastUpdate(lastUpdate);

                    onResponse.countryResponse(country);
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
        void countryResponse(Country country);

        void errorResponse(VolleyError error);
    }
}
