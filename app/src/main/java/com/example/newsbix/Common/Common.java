package com.example.newsbix.Common;

import com.example.newsbix.Interface.IconBetterIdeaService;
import com.example.newsbix.Interface.NewsService;
import com.example.newsbix.Model.IconBetterIdea;
import com.example.newsbix.Remote.IconBetterIdeaClient;
import com.example.newsbix.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common  {

    private static final String BASE_URL="http://newsapi.org/";

    public static final String API_KEY = "a312486b185e41afb3118f518b132a1d";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    //https://newsapi.org/v1/articles?source=the-verge&apiKey=a312486b185e41afb3118f518b132a1d

    //public static String getAPIUrl(String source,String sortBy,String API_KEY)
    public static String getAPIUrl(String source,String API_KEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiUrl.append(source).append("&apiKey=").append(API_KEY).toString();
    }

}
