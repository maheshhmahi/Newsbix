package com.example.newsbix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.newsbix.Adapter.ListNewsAdapter;
import com.example.newsbix.Common.Common;
import com.example.newsbix.Interface.NewsService;
import com.example.newsbix.Model.Article;
import com.example.newsbix.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    KenBurnsView kenBurnsView;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    NewsService mService;
    TextView top_author,top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String source="",sortBy="",webHotUrl="";

    ListNewsAdapter adapter;
    RecyclerView listNews;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        mService = Common.getNewsService();
        dialog = new SpotsDialog.Builder().setContext(ListNews.this).build();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source,true);
            }
        });

        diagonalLayout = (DiagonalLayout)findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detail = new Intent(getBaseContext(),detailArticle.class);
                detail.putExtra("webURL",webHotUrl);
                startActivity(detail);

            }
        });

        kenBurnsView = (KenBurnsView) findViewById(R.id.topImage);
        top_author = (TextView)findViewById(R.id.topAuthor);
        top_title = (TextView)findViewById(R.id.topTitle);

        listNews = (RecyclerView)findViewById(R.id.listNews);
        listNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        listNews.setLayoutManager(layoutManager);

        if(getIntent() != null)
        {
            source = getIntent().getStringExtra("Source");
            //sortBy = getIntent().getStringExtra("sortBy");

            //if(!source.isEmpty() && !sortBy.isEmpty())
            if(!source.isEmpty())
            {
                loadNews(source,false);
            }

        }
    }

    private void loadNews(String source, boolean isRefreshed) {
        if (!isRefreshed)
        {
            dialog.show();
            mService.getNewsArticles(Common.getAPIUrl(source,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();
                            try{
                                Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(kenBurnsView);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            //top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();

                            List<Article> removeFirstItem = response.body().getArticles();

                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem,getBaseContext());
                            adapter.notifyDataSetChanged();
                            listNews.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
        }
        else
        {
            dialog.show();
            mService.getNewsArticles(Common.getAPIUrl(source,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();
                            Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(kenBurnsView);
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            //top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();

                            List<Article> removeFirstItem = response.body().getArticles();

                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem,getBaseContext());
                            adapter.notifyDataSetChanged();
                            listNews.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
