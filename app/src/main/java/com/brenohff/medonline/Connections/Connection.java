package com.brenohff.medonline.Connections;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by breno on 07/08/2017.
 */

public class Connection {
    private static final String API_BASE = "https://medonline-backend.herokuapp.com/";

    private static Retrofit builder = new Retrofit.Builder()
            .baseUrl(API_BASE)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("dd/MM/yyyy").create()))
//            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()))
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return builder.create(serviceClass);
    }
}
