import { DatePipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Korisnik } from '../models/korisnik';
import { RezervacijeServisService } from '../services/rezervacije-servis.service';
import { Rezervacija } from '../models/rezervacija';
import { Vikendica } from '../models/vikendica';
import { VikendiceService } from '../services/vikendice.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-turista-rezervacije',
  standalone: true,
  imports: [FormsModule, DatePipe, RouterLink],
  templateUrl: './turista-rezervacije.component.html',
  styleUrl: './turista-rezervacije.component.css'
})
export class TuristaRezervacijeComponent implements OnInit{
  private rezervacijaServis = inject(RezervacijeServisService)
  private vikendiceServis = inject(VikendiceService)

  korisnik = new Korisnik()
  sveRezervacije : Array<Rezervacija> = []
  sveVikendice : Array<Vikendica> = []
  poruka = ""
  rezervacijeSaVikendicama: any[] = []
  aktuelneRezervacije: any[] = []
  prosleRezervacije: any[] = []

 
  poruka_otkazivanje: string = '';

  // Modal za komentar i ocenu
  komentarOcena: number = 0;
  izabranaRezervacija: any = null;
  commentText: string = '';
  rating: number = 0;
  komentarPoruka: string = '';

  ngOnInit(): void {
    let json = sessionStorage.getItem('korisnik')
    if(json){
      this.korisnik = JSON.parse(json)
      this.getReservations()
    }
    
  }

  getReservations() {
  this.vikendiceServis.getAll().subscribe((vikendiceData) => {
    if (vikendiceData) {
      this.sveVikendice = vikendiceData;

      this.rezervacijaServis.getAll().subscribe((rezervacijeData) => {
        if (rezervacijeData) {
          this.sveRezervacije = rezervacijeData;

          // filtriramo samo rezervacije trenutnog korisnika
          const mojeRezervacije = this.sveRezervacije.filter(
            (r) => r.turista_id === this.korisnik.id
          );

          // povezujemo sa vikendicama
          this.rezervacijeSaVikendicama = mojeRezervacije.map((r) => {
            const vik = this.sveVikendice.find(
              (v) => v.id === r.vikendica_id
            );
            return {
              ...r,
              naziv: vik ? vik.naziv : '',
              mesto: vik ? vik.mesto : '',
            };
          });

          const sada = new Date();

          // delimo na aktuelne i prošle
          this.aktuelneRezervacije = this.rezervacijeSaVikendicama.filter(
            (r) => new Date(r.datum_do) >= sada
          );

          this.prosleRezervacije = this.rezervacijeSaVikendicama
            .filter((r) => new Date(r.datum_do) < sada)
            .sort(
              (a, b) =>
                new Date(b.datum_od).getTime() -
                new Date(a.datum_od).getTime()
            );
        } else {
          this.poruka = 'Nismo dohvatili rezervacije';
        }
      });
    } else {
      this.poruka = 'Nismo dohvatili vikendice';
    }
  });
  
}

otkazi(r: any) {
    const danas = new Date();
    const datumOd = new Date(r.datum_od);
    const razlika = (datumOd.getTime() - danas.getTime()) / (1000 * 3600 * 24);

    /*if (razlika < 1) {
      this.poruka_otkazivanje = 'Резервацију је могуће отказати најкасније 1 дан пре почетка.';
      return;
    }*/

    /*this.rezervacijaServis.otkazi(r.id).subscribe({
      next: () => {
        this.poruka_otkazivanje = 'Резервација је успешно отказана.';
        this.getReservations(); // osveži prikaz
      },
      error: () => {
        this.poruka_otkazivanje = 'Грешка приликом отказивања.';
      }
    });*/
    this.rezervacijaServis.otkazi(r.id).subscribe({
      next: (res) => {
        if(res) {
          this.poruka_otkazivanje = 'Резервација је успешно отказана.';
          this.getReservations(); // osveži prikaz
        } else {
          this.poruka_otkazivanje = 'Грешка приликом отказивања.';
        }
      },
      error: () => {
        this.poruka_otkazivanje = 'Грешка приликом отказивања.';
      }
    });
  }

  // KOMENTAR I OCENA
  otvoriKomentarFormu(r: any) {
    this.izabranaRezervacija = r;
    this.commentText = '';
    this.rating = 0;
    this.komentarOcena = 1;
  }

  zatvoriKomentar() {
    this.komentarOcena = 0;
    this.commentText = '';
    this.rating = 0;
    this.komentarPoruka = '';
  }

  mozePoslatiKomentar(): boolean {
    return this.rating > 0;
  }

  posaljiKomentar() {
    if (!this.izabranaRezervacija) return;

    const payload = {
      rezervacija_id: this.izabranaRezervacija.id,
      komentar: this.commentText,
      ocena: this.rating
    };

    /*this.rezervacijaServis.dodajKomentar(payload).subscribe({
      next: () => {
        this.komentarPoruka = 'Коментар успешно послат.';
        this.getReservations();
        setTimeout(() => this.zatvoriKomentar(), 2000);
      },
      error: () => {
        this.komentarPoruka = 'Грешка приликом слања коментара.';
      }
    });*/
  }
}

  

