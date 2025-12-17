package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.dao.RezervacijaRepo;
import com.example.demo.db.dao.VikendicaRepo;
import com.example.demo.models.Rezervacija;
import com.example.demo.models.Vikendica;

@RestController
@RequestMapping("/rezervacije")
@CrossOrigin(origins = "http://localhost:4200")
public class RezervacijaKontroler {
    @PostMapping("/posaljiRezervaciju")
    public Rezervacija sendReservation(@RequestBody Rezervacija r){
        return new RezervacijaRepo().posaljiRezervaciju(r);
    }

    @GetMapping("/getAll")
    public List<Rezervacija> getAll(){
        return new RezervacijaRepo().getAll();
    } 

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}/potvrdi")
    public boolean potvrdiRezervaciju(@PathVariable int id) {
        return new RezervacijaRepo().potvrdiRezervaciju(id);
    }

   
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}/odbij")
    public boolean odbijRezervaciju(@PathVariable int id, @RequestBody Rezervacija r) {
        return new RezervacijaRepo().odbijRezervaciju(id, r.getKomentar());
    }


    @GetMapping("/statistika/meseci/{vlasnikId}")
    public Object getRezervacijePoMesecima(@PathVariable int vlasnikId) {
        return new RezervacijaRepo().rezervacijePoMesecima(vlasnikId);
    }

    @GetMapping("/statistika/vikendi/{vlasnikId}")
    public Object getVikendVsRadniDani(@PathVariable int vlasnikId) {
        return new RezervacijaRepo().vikendVsRadniDani(vlasnikId);
    }

    @PutMapping("/{id}/otkazi")
    public boolean otkaziRezervaciju(@PathVariable int id) {
        return new RezervacijaRepo().otkaziRezervaciju(id);
    }


}
