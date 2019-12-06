package com.example.jisu.musicproject;

public class DataList {
    String name;
    String artist;
    int time;
    int scroe;

    public DataList() { }

    public DataList(String name, String artist, int time, int scroe) {
        this.name = name;
        this.artist = artist;
        this.time = time;
        this.scroe = scroe;
    }
    public DataList(String name, String artist,  int scroe) {
        this.name = name;
        this.artist = artist;
        this.scroe = scroe;
    }
    public DataList(String name,int time) {
        this.name = name;
        this.time = time;
    }
    public DataList(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getScroe() {
        return scroe;
    }

    public void setScroe(int scroe) {
        this.scroe = scroe;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DataList{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", time=" + time +
                ", scroe=" + scroe +
                '}';
    }
}
