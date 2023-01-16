package com.example.utbprekladac.pojo;

import com.squareup.moshi.Json;

public class ResponseData {
    @Json(name = "translatedText")
    public String translatedText;
}