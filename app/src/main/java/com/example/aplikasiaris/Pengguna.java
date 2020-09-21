package com.example.aplikasiaris;

class Pengguna extends PenggunaId{

    String nama;
    String jumlah;
    String deskripsi;
    String kategory;

    public Pengguna() {
    }

    public Pengguna(String nama, String jumlah, String deskripsi, String kategory) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;
        this.kategory = kategory;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setKategory(String kategory) {
        this.kategory = kategory;
    }

    public String getNama() {
        return nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getKategory() {
        return kategory;
    }
}
