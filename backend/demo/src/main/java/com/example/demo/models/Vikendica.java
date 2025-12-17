package com.example.demo.models;

import java.sql.Date;

public class Vikendica {
    private int id;
    private String naziv;
    private String mesto;
    private String opis;
    private float cena;
    private int vlasnik_id;
    
    public Vikendica(int id, String naziv, String mesto, String opis, float cena, int vlasnik_id, String slika,
            Date datum_dodavanja, int broj_soba) {
        this.id = id;
        this.naziv = naziv;
        this.mesto = mesto;
        this.opis = opis;
        this.cena = cena;
        this.vlasnik_id = vlasnik_id;
        this.slika = slika;
        this.datum_dodavanja = datum_dodavanja;
        this.broj_soba = broj_soba;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getMesto() {
        return mesto;
    }
    public void setMesto(String mesto) {
        this.mesto = mesto;
    }
    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) {
        this.opis = opis;
    }
    public float getCena() {
        return cena;
    }
    public void setCena(float cena) {
        this.cena = cena;
    }
    public int getVlasnik_id() {
        return vlasnik_id;
    }
    public void setVlasnik_id(int vlasnik_id) {
        this.vlasnik_id = vlasnik_id;
    }
    public String getSlika() {
        return slika;
    }
    public void setSlika(String slika) {
        this.slika = slika;
    }
    public Date getDatum_dodavanja() {
        return datum_dodavanja;
    }
    public void setDatum_dodavanja(Date datum_dodavanja) {
        this.datum_dodavanja = datum_dodavanja;
    }
    public int getBroj_soba() {
        return broj_soba;
    }
    public void setBroj_soba(int broj_soba) {
        this.broj_soba = broj_soba;
    }
    private String slika;
    private Date datum_dodavanja;
    private int broj_soba;
    public Vikendica() {
    // obavezan za Jackson i JPA
    }


    
}
