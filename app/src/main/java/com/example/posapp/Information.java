package com.example.posapp;

public class Information {
    private String restro;
    private String cost;
    private String items;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getRestro() {
        return restro;
    }

    public void setRestro(String restro) {
        this.restro = restro;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Information() {
    }

    public Information(String restro, String cost, String items, long time) {
        this.restro = restro;
        this.cost = cost;
        this.items = items;
        this.time = time;
    }
}
