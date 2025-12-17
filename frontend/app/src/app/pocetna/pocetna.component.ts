import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { StatistikaService } from '../services/statistika.service';
import { Statistika } from '../models/statistika';
import { RouterLink } from '@angular/router';
import { Vikendica } from '../models/vikendica';
import { VikendiceService } from '../services/vikendice.service';

@Component({
  selector: 'app-pocetna',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './pocetna.component.html',
  styleUrl: './pocetna.component.css'
})
export class PocetnaComponent implements OnInit {

  private statServis = inject(StatistikaService)
  private vikendicaServis = inject(VikendiceService)
  statistika = new Statistika()
  sveVikendice1 : Array<Vikendica> = []
  sveVikendice : Array<Vikendica> = []
  poruka = ""
  naziv = ""
  mesto = ""
  sortirajPO = ""
  sortirajKAKO = ""
  ngOnInit(): void {
    this.getStitistika()
    this.dajSve()
    
  }

  getStitistika(){
    this.statServis.getAll().subscribe(data => {
      if(data){
        this.statistika = data
      }else

        console.error('Nismo dohvatili statistike')
    })
  }

  dajSve(){
    this.vikendicaServis.getAll().subscribe((data)=>{
      if(data){
        this.sveVikendice1 = data
      }
    })
  }

  pretrazi(){
    this.sveVikendice = [];
    this.poruka = ""
    if(this.naziv != ""){
      for(let v of this.sveVikendice1){
        if(v.naziv == this.naziv){
          this.sveVikendice.push(v)
        }
      }
    }else if(this.mesto != ""){
      for(let v of this.sveVikendice1){
        if(v.mesto == this.mesto){
          this.sveVikendice.push(v)
        }
      }

    }else if(this.mesto == "" && this.naziv == ""){
      this.poruka = "Morate uneti neki kriterijum pretrage(naziv ili mesto)"
      return
    }
    if(this.sveVikendice.length == 0){
      this.poruka = "Nema u ponudi takvih vikendica"
    }
    if(this.sortirajPO == "naziv"){
      this.sveVikendice.sort((v1, v2) => {
        const rez = v1.naziv.localeCompare(v2.naziv); // upoređuje stringove leksički
        return this.sortirajKAKO === "opadajuce" ? -rez : rez;
      });
    }else if(this.sortirajKAKO == "cena"){
      this.sveVikendice.sort((v1, v2) => 
        this.sortirajKAKO === "opadajuce" ? v2.cena - v1.cena : v1.cena - v2.cena
      );

    }else if(this.sortirajKAKO == "mesto"){
      this.sveVikendice.sort((v1, v2) => {
        const rez = v1.mesto.localeCompare(v2.mesto); // upoređuje stringove leksički
        return this.sortirajKAKO === "opadajuce" ? -rez : rez;
      });

    }

    

  }

}
