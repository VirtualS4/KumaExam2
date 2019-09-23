package com.example.rona.kumaexam2;

public class Notes {

    String id,judul,deskripsi,tanggal;

    public Notes(){

    }

    public Notes(String id,String name,String deskripsi,String tanggal){
        this.tanggal = tanggal;
        this.id = id;
        this.judul = name;
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }
}
