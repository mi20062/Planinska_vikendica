package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.dao.StatistikaRepo;
import com.example.demo.models.Statistika;

@RestController
@RequestMapping("/statistika")
@CrossOrigin(origins = "http://localhost:4200")
public class StatistikaKontroler {
    @GetMapping("/getAll")
    public Statistika getStatistika(){
        return new StatistikaRepo().getAll();
    }
    
}
