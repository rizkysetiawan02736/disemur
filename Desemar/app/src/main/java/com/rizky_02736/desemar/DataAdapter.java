package com.rizky_02736.desemar;

public class DataAdapter {
    public String adapter_id;
    public String adapter_judul;
    public String adapter_deskripsi;
    public String adapter_url;

    public String getid(){return adapter_id;}
    public void setid(String id){
        this.adapter_id=id;

    }
    public String getjudul(){return adapter_judul;}
    public void setjudul(String judul){
        this.adapter_judul=judul;

    }

    public String getdeskripsi(){return adapter_deskripsi;}
    public void setdeskripsi(String deskripsi){
        this.adapter_deskripsi=deskripsi;

    }

    public String geturl(){return adapter_url;}
    public void seturl(String url){
        this.adapter_url=url;

    }
}
