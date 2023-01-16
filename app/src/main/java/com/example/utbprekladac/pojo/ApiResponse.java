package com.example.utbprekladac.pojo;


import com.squareup.moshi.Json;

public class ApiResponse {
    @Json(name = "responseData")
    public ResponseData responseData;
}