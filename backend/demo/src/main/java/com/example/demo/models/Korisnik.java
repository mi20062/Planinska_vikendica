package com.example.demo.models;



public class Korisnik {
    private int id;
    private String korisnicko_ime;
    private String ime;
    private String prezime;
    private String lozinka;
    private String uloga;
    private String email;
    public Korisnik(int id, String korisnicko_ime, String ime, String prezime, String lozinka, String uloga,
            String email, String pol, String adresa, String kontakt_telefon, String profilna_slika,
            String broj_kreditne_kartice, int aktivan, String status) {
        this.id = id;
        this.korisnicko_ime = korisnicko_ime;
        this.ime = ime;
        this.prezime = prezime;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.email = email;
        this.pol = pol;
        this.adresa = adresa;
        this.kontakt_telefon = kontakt_telefon;
        this.profilna_slika = profilna_slika;
        this.broj_kreditne_kartice = broj_kreditne_kartice;
        this.aktivan = aktivan;
        this.status = status;
    }
    private String pol;
    private String adresa;
    private String kontakt_telefon;
    private String profilna_slika;
    public int getAktivan() {
        return aktivan;
    }
    public void setAktivan(int aktivan) {
        this.aktivan = aktivan;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    private String broj_kreditne_kartice;
    private int aktivan;
    private String status;
    
    public Korisnik(int id, String korisnicko_ime, String ime, String prezime, String lozinka, String uloga,
            String email, String pol, String adresa, String kontakt_telefon, String profilna_slika,
            String broj_kreditne_kartice) {
        this.id = id;
        this.korisnicko_ime = korisnicko_ime;
        this.ime = ime;
        this.prezime = prezime;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.email = email;
        this.pol = pol;
        this.adresa = adresa;
        this.kontakt_telefon = kontakt_telefon;
        this.profilna_slika = profilna_slika;
        this.broj_kreditne_kartice = broj_kreditne_kartice;

        
    }
    public Korisnik() {
    // obavezan za Jackson i JPA
    }

    public Korisnik(int id, String korisnicko_ime, String ime, String prezime, String lozinka, String uloga,
            String email, String pol, String adresa, String kontakt_telefon, 
            String broj_kreditne_kartice) {
        this.id = id;
        this.korisnicko_ime = korisnicko_ime;
        this.ime = ime;
        this.prezime = prezime;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.email = email;
        this.pol = pol;
        this.adresa = adresa;
        this.kontakt_telefon = kontakt_telefon;
        
        this.broj_kreditne_kartice = broj_kreditne_kartice;
       
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }
    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getLozinka() {
        return lozinka;
    }
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public String getUloga() {
        return uloga;
    }
    public void setUloga(String uloga) {
        this.uloga = uloga;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPol() {
        return pol;
    }
    public void setPol(String pol) {
        this.pol = pol;
    }
    public String getAdresa() {
        return adresa;
    }
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    public String getKontakt_telefon() {
        return kontakt_telefon;
    }
    public void setKontakt_telefon(String kontakt_telefon) {
        this.kontakt_telefon = kontakt_telefon;
    }
    public String getProfilna_slika() {
        return profilna_slika;
    }
    public void setProfilna_slika(String profilna_slika) {
        this.profilna_slika = profilna_slika;
    }
    public String getBroj_kreditne_kartice() {
        return broj_kreditne_kartice;
    }
    public void setBroj_kreditne_kartice(String broj_kreditne_kartice) {
        this.broj_kreditne_kartice = broj_kreditne_kartice;
    }
    
    
}
