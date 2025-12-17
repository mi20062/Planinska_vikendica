import { DatePipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Vikendica } from '../models/vikendica';
import { Korisnik } from '../models/korisnik';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { VikendiceService } from '../services/vikendice.service';
import { RezervacijeServisService } from '../services/rezervacije-servis.service';
import { Rezervacija } from '../models/rezervacija';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-detalji-vikendice',
  standalone: true,
  imports: [FormsModule, DatePipe,RouterLink],
  templateUrl: './detalji-vikendice.component.html',
  styleUrl: './detalji-vikendice.component.css'
})
export class DetaljiVikendiceComponent implements OnInit{
  private activeRoute = inject(ActivatedRoute) 
  private vikendiceServis = inject(VikendiceService)
  private rezervacijeServis = inject(RezervacijeServisService)
  private sanitizer = inject(DomSanitizer)

  reservationData = new Rezervacija()
  vikendica = new Vikendica
  sveVikendice : Array<Vikendica> = []
  korak: number = 1;   
  pocetak: string = '';  
  kraj: string = '';     
  odrasli: number = 1;   
  deca: number = 0;     
  profileImgUrl!: SafeUrl;

  
  brojKartice: string = '';  
  zahtev: string = '';       
  
  ukupnaCena: number = 0;

  
  poruka: string = '';

  korisnik = new Korisnik()
  
  id = -1
  ngOnInit(): void {
    this.activeRoute.params.subscribe(params => {
      this.id = parseInt(params['id'])
      this.getAppartmentDetails()
      //this.getComments()
    }) 

    let json = sessionStorage.getItem('korisnik')
    if(json){
      this.korisnik = JSON.parse(json)
      this.reservationData.turista_id = this.korisnik.id
      this.reservationData.vikendica_id = this.id

    }
  }
  getAppartmentDetails(){
    this.vikendiceServis.getAll().subscribe(data => {
      console.log(data)
      if(data){
        this.sveVikendice = data
        for(let v of this.sveVikendice){
          if(v.id == this.id){
            this.vikendica = v
          }
        }
        if (this.vikendica.slika) {

          this.profileImgUrl = this.sanitizer.bypassSecurityTrustUrl(
            `data:image/png;base64,${this.vikendica.slika}`
          )
    } else {
      // Ako nema, koristi statičku iz assets foldera
      //this.convertImageToBase64('assets/planinaAkvarel.jpg');
    }
      }else{
        this.poruka = "Nismo dohvatili vikendice"
      }
    })
  }



  predjiNaKorak2(){
    this.korak = 2
    this.ukupnaCena = this.odrasli * this.vikendica.cena
    this.reservationData.datum_od = new Date(this.pocetak);
    this.reservationData.datum_do = new Date(this.kraj);
    this.reservationData.status = "rezervisano"
    
  }


  canReserve(){
    if(this.pocetak == "" || this.kraj == "" ){
      this.poruka = "Unesite sve sto je potrebno"
      return false
    }
    return true
    
  }

  potvrdiZakazivanje(){
    if(!this.canReserve())
      return
    
    //this.reservationData.amount = this.calculateTotal()
    this.rezervacijeServis.posaljiRezervaciju(this.reservationData).subscribe(data => {
      if(data){
         //this.ngOnInit()
         this.poruka = "Uspesnoo prijavljeni!"
      }else{
        console.log(this.reservationData)
        this.poruka ='Greska prilikom slanja podataka za rezervisanje, molimo pokusajte kasnije'
      }
    })

  }

  

}
