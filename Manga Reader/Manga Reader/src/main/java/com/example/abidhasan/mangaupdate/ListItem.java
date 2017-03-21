package com.example.abidhasan.mangaupdate;

/**
 * Created by Abid Hasan on 2/21/2017.
 */

public class ListItem {

    private String manga;
    private String desc;
    private String MangaViewLink;
    private String imageUrl;


    public ListItem(String manga, String desc, String imageUrl, String MangaViewLink) {
        this.manga = manga;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.MangaViewLink=MangaViewLink;


    }

    public String getManga() {
        return manga;
    }

    public String getDesc() {
        return desc;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getMangaViewLink() {
        return MangaViewLink;
    }





}

