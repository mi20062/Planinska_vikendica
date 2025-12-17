package com.example.demo.models;


import java.sql.Date;

public class Rezervacija {

    private int id;
    private int vikendica_id;
    private int turista_id;
    private Date datum_od;
    private Date datum_do;
    private String komentar;
    private String status;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVikendica_id() {
        return vikendica_id;
    }

    public void setVikendica_id(int vikendica_id) {
        this.vikendica_id = vikendica_id;
    }

    public int getTurista_id() {
        return turista_id;
    }

    public void setTurista_id(int turista_id) {
        this.turista_id = turista_id;
    }

    public Date getDatum_od() {
        return datum_od;
    }

    public void setDatum_od(Date datum_od) {
        this.datum_od = datum_od;
    }

    public Date getDatum_do() {
        return datum_do;
    }

    public void setDatum_do(Date datum_do) {
        this.datum_do = datum_do;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getKomentar() { return komentar; }
    public void setKomentar(String komentar) { this.komentar = komentar; }

    public Rezervacija(int id, int vikendica_id, int turista_id, Date datum_od, Date datum_do,
                       String status, String komentar) {
        this.id = id;
        this.vikendica_id = vikendica_id;
        this.turista_id = turista_id;
        this.datum_od = datum_od;
        this.datum_do = datum_do;
        this.status = status;
        this.komentar = komentar;
    }
    
}
