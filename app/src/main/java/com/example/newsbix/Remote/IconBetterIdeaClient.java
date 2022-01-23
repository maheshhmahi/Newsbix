package com.example.newsbix.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IconBetterIdeaClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl("https://vision-icon.netop.com/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
