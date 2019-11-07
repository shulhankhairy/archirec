package com.hexa.mobile.archirecon.item;

public class berita_item {
    private String id,title, content, image_link;
    private int thumbnail;


    public berita_item(String id,String title, String content, String image_link) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.image_link = image_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}