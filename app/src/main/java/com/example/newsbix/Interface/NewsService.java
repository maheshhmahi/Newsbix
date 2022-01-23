package com.example.newsbix.Interface;

import com.example.newsbix.Model.News;
import com.example.newsbix.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

    @GET
    Call<News> getNewsArticles(@Url String url);
}
