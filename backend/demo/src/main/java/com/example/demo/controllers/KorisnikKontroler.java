package com.example.demo.controllers;




import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.db.dao.KorisnikRepo;
import com.example.demo.models.Korisnik;

@RestController
@RequestMapping("/korisnici")
@CrossOrigin(origins = "http://localhost:4200")
public class KorisnikKontroler {
     @PostMapping("/login")
    public Korisnik login(@RequestBody Korisnik k){
        return new KorisnikRepo().login(k);
    }

    @PostMapping("/azuriraj")
    public Korisnik azuriraj(@RequestParam("id") int id,
        @RequestParam("korisnicko_ime") String korisnicko_ime,
        @RequestParam("ime") String ime,
        @RequestParam("prezime") String prezime,
        @RequestParam("uloga") String uloga,
        @RequestParam("email") String email,
        @RequestParam("lozinka") String lozinka,
        @RequestParam("pol") String pol,
        @RequestParam("broj_kreditne_kartice") String broj_kreditne_kartice,
        @RequestParam("kontakt_telefon") String kontakt_telefon,
        @RequestParam("adresa") String adresa,
        
        @RequestParam(value="profilna_slika", required=false) MultipartFile profilna_slika
    ){
        Korisnik user = new KorisnikRepo().pronadjiID(id);

        user.setKorisnicko_ime(korisnicko_ime);
        user.setIme(ime);
        user.setPrezime(prezime);
        user.setUloga(uloga);
        user.setEmail(email);
        user.setLozinka(lozinka);
        user.setPol(pol);
        user.setBroj_kreditne_kartice(broj_kreditne_kartice);
        user.setKontakt_telefon(kontakt_telefon);
        user.setAdresa(adresa);

        if (profilna_slika != null && !profilna_slika.isEmpty()) {
            user.setProfilna_slika(profilna_slika.getOriginalFilename());
        }

        return new KorisnikRepo().azuriraj(user, profilna_slika);
                
        
    }


     @PostMapping("/registruj")
    public Korisnik registruj(@RequestParam("id") int id,
        @RequestParam("korisnicko_ime") String korisnicko_ime,
        @RequestParam("ime") String ime,
        @RequestParam("prezime") String prezime,
        @RequestParam("uloga") String uloga,
        @RequestParam("email") String email,
        @RequestParam("lozinka") String lozinka,
        @RequestParam("pol") String pol,
        @RequestParam("broj_kreditne_kartice") String broj_kreditne_kartice,
        @RequestParam("kontakt_telefon") String kontakt_telefon,
        @RequestParam("adresa") String adresa,
        
        @RequestParam(value="profilna_slika", required=false) MultipartFile profilna_slika
    ){
        Korisnik user = new Korisnik(
            id,
            korisnicko_ime,
            ime,
            prezime,
            lozinka,
            uloga,
            email,
            pol,
            adresa,
            kontakt_telefon,
            
            broj_kreditne_kartice
        );
         if (profilna_slika != null && !profilna_slika.isEmpty()) {
            user.setProfilna_slika(profilna_slika.getOriginalFilename());
        }

                
        return new KorisnikRepo().registruj(user, profilna_slika);      
        
    }
}
