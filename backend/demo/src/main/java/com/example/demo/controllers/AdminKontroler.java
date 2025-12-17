package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.dao.KorisnikRepo;
import com.example.demo.db.dao.VikendicaRepo;
import com.example.demo.models.Korisnik;
import com.example.demo.models.Vikendica;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminKontroler {

    private final KorisnikRepo korisnikRepo = new KorisnikRepo();
    private final VikendicaRepo vikendicaRepo = new VikendicaRepo();

   
    @GetMapping("/korisnici")
    public List<Korisnik> getAllKorisnici() {
        return new KorisnikRepo().getAll();
    }

    // 2. Деактивација корисника
    @PutMapping("/korisnik/deaktiviraj/{id}")
    public boolean deaktiviraj(@PathVariable int id) {
        return korisnikRepo.deaktiviraj(id);
    }

    // 3. Брисање корисника
    @DeleteMapping("/korisnik/obrisi/{id}")
    public boolean obrisi(@PathVariable int id) {
        return korisnikRepo.obrisi(id);
    }

    // 4. Разматрање захтева за регистрацију
    @GetMapping("/zahtevi")
    public List<Korisnik> getPendingZahtevi() {
        return korisnikRepo.getPending();
    }

    @PutMapping("/zahtev/{id}/prihvati")
    public boolean prihvatiZahtev(@PathVariable int id) {
        return korisnikRepo.prihvatiZahtev(id);
    }

    @PutMapping("/zahtev/{id}/odbij")
    public boolean odbijZahtev(@PathVariable int id) {
        return korisnikRepo.odbijZahtev(id);
    }

    // 5. Преглед свих викендица
    @GetMapping("/vikendice")
    public List<Vikendica> getAllVikendice() {
        return vikendicaRepo.getAll();
    }

    // 6. Привремено блокирање викендице 48h
   /*  @PutMapping("/vikendica/blokiraj/{id}")
    public boolean blokirajVikendicu(@PathVariable int id) {
        return vikendicaRepo.privremenoBlokiraj(id, 48); // 48h
    }*/
}
