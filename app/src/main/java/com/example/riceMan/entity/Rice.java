package com.example.riceMan.entity;

import java.io.Serializable;

public class Rice implements Serializable {
    private int id;
    private String name;
    private String price;
    private String imgurl;
    private int flag;

    @Override
    public String toString() {
        return "Rice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", flag=" + flag +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Rice() {
        this.flag = 0;
    }
}
