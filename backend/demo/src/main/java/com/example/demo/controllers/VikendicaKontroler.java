package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.db.dao.VikendicaRepo;
import com.example.demo.models.Vikendica;

@RestController
@RequestMapping("/vikendice")
@CrossOrigin(origins = "http://localhost:4200")
public class VikendicaKontroler {
    @GetMapping("/getAll")
    public List<Vikendica> getAll(){
        return new VikendicaRepo().getAll();
    } 

    @GetMapping("/vlasnik/{id}")
    public List<Vikendica> getByVlasnik(@PathVariable int id) {
        return new VikendicaRepo().getByVlasnik(id);
    }

    @PostMapping("/dodaj")
    public Vikendica dodajVikendicu(
            @RequestParam String naziv,
            @RequestParam String mesto,
            @RequestParam String opis,
            @RequestParam int broj_soba,
            @RequestParam float cena,
            @RequestParam int vlasnik_id,
            @RequestParam(required = false) MultipartFile slika,
            @RequestParam(required = false) MultipartFile jsonFajl
    ) {
        Vikendica v = new Vikendica();
        v.setNaziv(naziv);
        v.setMesto(mesto);
        v.setOpis(opis);
        v.setBroj_soba(broj_soba);
        v.setCena(cena);
        v.setVlasnik_id(vlasnik_id); // vlasnik_id fiksno
        return new VikendicaRepo().dodajVikendicu(v, slika, jsonFajl);
    }

    @PutMapping("/izmeni")
    public boolean izmeni(@RequestBody Vikendica v) {
        return new VikendicaRepo().izmeniVikendicu(v);
    }

    @DeleteMapping("/obrisi/{id}")
    public boolean obrisi(@PathVariable int id) {
        return new VikendicaRepo().obrisiVikendicu(id);
    }
}
