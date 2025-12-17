package com.example.demo.models;

public class Statistika{
    private int broj_vikendica;
    private int broj_vlasnika;
    private int broj_turista;
    private int broj_danas;
    private int broj_nedelja;
    private int broj_mesec;
    public Statistika(int broj_vikendica, int broj_vlasnika, int broj_turista, int broj_danas, int broj_nedelja,
            int broj_mesec) {
        this.broj_vikendica = broj_vikendica;
        this.broj_vlasnika = broj_vlasnika;
        this.broj_turista = broj_turista;
        this.broj_danas = broj_danas;
        this.broj_nedelja = broj_nedelja;
        this.broj_mesec = broj_mesec;
    }
    public int getBroj_vikendica() {
        return broj_vikendica;
    }
    public void setBroj_vikendica(int broj_vikendica) {
        this.broj_vikendica = broj_vikendica;
    }
    public int getBroj_vlasnika() {
        return broj_vlasnika;
    }
    public void setBroj_vlasnika(int broj_vlasnika) {
        this.broj_vlasnika = broj_vlasnika;
    }
    public int getBroj_turista() {
        return broj_turista;
    }
    public void setBroj_turista(int broj_turista) {
        this.broj_turista = broj_turista;
    }
    public int getBroj_danas() {
        return broj_danas;
    }
    public void setBroj_danas(int broj_danas) {
        this.broj_danas = broj_danas;
    }
    public int getBroj_nedelja() {
        return broj_nedelja;
    }
    public void setBroj_nedelja(int broj_nedelja) {
        this.broj_nedelja = broj_nedelja;
    }
    public int getBroj_mesec() {
        return broj_mesec;
    }
    public void setBroj_mesec(int broj_mesec) {
        this.broj_mesec = broj_mesec;
    }
}