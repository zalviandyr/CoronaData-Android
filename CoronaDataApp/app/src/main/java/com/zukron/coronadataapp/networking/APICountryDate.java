package com.zukron.coronadataapp.networking;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.zukron.coronadataapp.model.Country;
import com.zukron.coronadataapp.tools.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/12/2020
 */
public class APICountryDate {
    private Context context;
    private OnResponse onResponse;

    public APICountryDate(Context context, OnResponse onResponse) {
        this.context = context;
        this.onResponse = onResponse;
    }

    public void getCountryByDateInterval(String country, int interval) {
        AndroidThreeTen.init(context);

        String slug = Country.getCountrySlug(country);
        String dateFrom = LocalDate.now().minusDays(interval).toString().concat("T00:00:00Z");
        String dateTo = LocalDate.now().toString().concat("T00:00:00Z");
        String url = MessageFormat.format(Covid19API.countryByInterval, slug, dateFrom, dateTo);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    ArrayList<Country> countries = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        LocalDateTime lastUpdate = LocalDateTime.parse(json.getString("Date"), dateTimeFormatter);

                        Country country = new Country();
                        country.setName(json.getString("Country"));
                        country.setIso2(json.getString("CountryCode"));
                        country.setConfirmed(json.getInt("Confirmed"));
                        country.setRecovered(json.getInt("Recovered"));
                        country.setDeaths(json.getInt("Deaths"));
                        country.setLastUpdate(lastUpdate);

                        countries.add(country);
                    }

                    onResponse.countriesResponse(countries);
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
        void countriesResponse(ArrayList<Country> countries);

        void errorResponse(VolleyError error);
    }
}
