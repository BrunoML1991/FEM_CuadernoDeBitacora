package com.example.bruno.cuadernodebitacora;

import com.example.bruno.cuadernodebitacora.pojos.Temperature;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SuppressWarnings("Unused")
interface TemperatureRESTAPIService {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    // https://restcountries.eu/rest/v2/name/España
    @GET("find?lat=40.39354&lon=-3.662&cnt=20&APPID=cf12f7ed0c2c47a7dcf70ad2617be4dc")
    Call<List<Temperature>> getCountryByName();

}
