package com.example.utbprekladac;

import com.example.utbprekladac.pojo.ApiResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation
    @GET("get")
    Call<ApiResponse> getResponse(@Query("q") String q,
                                  @Query("langpair") String langpair);
}
