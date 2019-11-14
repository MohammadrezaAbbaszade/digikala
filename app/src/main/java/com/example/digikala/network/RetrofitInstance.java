package com.example.digikala.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance  {
    private static RetrofitInstance instance;
    private Retrofit mRetrofit;

    public static RetrofitInstance getInstance(String baseUrl) {
        if (instance == null) {
            instance = new RetrofitInstance(baseUrl);
        }

        return instance;
    }

    private RetrofitInstance(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(createGsonConverter(new TypeToken<List<PhotoItem>>() {}.getType(), new GetItemListDeserializer()))
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
