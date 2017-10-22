package com.example.lenovo.Album1.Activity.recyclerview.Database;

public class Movie {

    private String movie_id;
    private String name;
    private int count;
    private String price;
    private String date;
    private String dir;
    private String lan;
    private String kind;
    //Bitmap image;
    private String rul;
    private String dur;
    private String made;
    private String haj;
    private String s1;
    private String s2;
    private String s3;
    private String des;
    private String tumbnail;

    public Movie(String lan
            , String dir, String dur
            , String haj, String des
            , String rul, String made) {

        this.made=made;
        this.dir=dir;
        this.dur=dur;
        this.lan=lan;
        this.des=des;
        this.haj=haj;
        this.rul=rul;
    }

    public Movie() {}


    public Movie(String id, String name, String price, String image) {

        this.name = name;
        this.price = price;
        this.tumbnail = image;
        this.movie_id=id;

    }

    public Movie(String id,String name, String price, String image, String date, int count) {
        this.name=name;
        this.price=price;
        this.tumbnail=image;
        this.date=date;
        this.count=count;
        this.movie_id=id;
    }



    public String getMovie_ID() {
        return movie_id;
    }
    public void setMovie_ID(String movie_id) {
        this.movie_id = movie_id;
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTumbnail() {
        return tumbnail;
    }
    public void setTumbnail(String tumbnail) {
        this.tumbnail = tumbnail;
    }
    public String getLan() {
        return lan;
    }
    public void setLan(String lan) {
        this.lan = lan;
    }
    public void setDur(String dur) {
        this.dur = dur;
    }
    public String getDur() {
        return dur;
    }
    public void setRul(String rul) {
        this.rul = rul;
    }
    public String getRul() {
        return rul;
    }
    public void setDir(String dir) {
        this.dir = dir;
    }
    public String getDir() {
        return dir;
    }
    public void setMade(String made) {
        this.made = made;
    }
    public String getMade() {
        return made;
    }
    public void setDes(String des) {
        this.des = des;
    }
    public String getDes() {
        return des;
    }

    public void setHaj(String haj) {
        this.haj = haj;
    }
    public String getHaj() {
        return haj;
    }

    public void setS1(String S1) {
        this.s1= S1;
    }
    public String getS1() {
        return s1;
    }

    public void setS2(String S2) {
        this.s2 = S2;
    }
    public String getS2() {
        return s2;
    }

    public void setS3(String S3) {
        this.s3 = S3;
    }
    public String getS3() {
        return s3;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }



}


