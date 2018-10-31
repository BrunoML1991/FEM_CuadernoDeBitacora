package com.example.bruno.cuadernodebitacora;

import com.example.bruno.cuadernodebitacora.pojos.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

@SuppressWarnings("Unused")
interface BookRESTAPIService {

    @GET("lists/best-sellers/history.json?api-key=b02cc3a87d0c4a9e900003cedb900a22")
    Call<ApiResponse> getCountryByName();

}
