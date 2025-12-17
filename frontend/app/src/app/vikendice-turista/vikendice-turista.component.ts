import { Component, inject, OnInit } from '@angular/core';
import { VikendiceService } from '../services/vikendice.service';
import { Vikendica } from '../models/vikendica';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-vikendice-turista',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './vikendice-turista.component.html',
  styleUrl: './vikendice-turista.component.css'
})
export class VikendiceTuristaComponent implements OnInit {
  private router = inject(Router)
  
  private vikendicaServis = inject(VikendiceService)
    
  sveVikendice1 : Array<Vikendica> = []
  sveVikendice : Array<Vikendica> = []
  poruka = ""
  naziv = ""
  mesto = ""
  sortirajPO = ""
  sortirajKAKO = ""

  ngOnInit(): void {
    this.dajSve()
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
  detalji(idA: number){
    this.router.navigate(['detaljiVikendice', idA])
  }


}
