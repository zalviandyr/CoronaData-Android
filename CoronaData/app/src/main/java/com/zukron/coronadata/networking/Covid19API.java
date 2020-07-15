package com.zukron.coronadata.networking;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/13/2020
 */
public interface Covid19API {
    String baseUrl = "https://api.covid19api.com/";
    String countryByInterval = baseUrl + "country/{0}?from={1}&to={2}";
}
