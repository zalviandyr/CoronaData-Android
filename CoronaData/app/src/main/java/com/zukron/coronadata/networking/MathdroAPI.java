package com.zukron.coronadata.networking;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/12/2020
 */
public interface MathdroAPI {
    String BaseUrl = "https://covid19.mathdro.id/api/";
    String CountryByIso = BaseUrl + "countries/{0}";
    String CountryByDate = BaseUrl + "daily/{0}";
}
