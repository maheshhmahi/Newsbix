package com.example.newsbix.Model;


import java.util.List;

public class News {
    private String stauts;
    private String source;
    private String sortBy;
    private List<Article> articles;

    public News(String stauts, String source, String sortBy, List<Article> articles) {
        this.stauts = stauts;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
