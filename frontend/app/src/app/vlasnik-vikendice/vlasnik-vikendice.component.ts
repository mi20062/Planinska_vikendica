import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Korisnik } from '../models/korisnik';

@Component({
  selector: 'app-vlasnik-vikendice',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './vlasnik-vikendice.component.html',
  styleUrls: ['./vlasnik-vikendice.component.css']
})
export class VlasnikVikendiceComponent implements OnInit {
  vikendice: any[] = [];
  nova: any = {};
  slika?: File;
  jsonFajl?: File;
  vlasnikId = 1;

  // za uređivanje
  prikaziFormuZaIzmenu = false;
  zaIzmenu: any = {};
  korisnik = new Korisnik()

  constructor(private http: HttpClient) {}

  ngOnInit() {
    let json = sessionStorage.getItem('korisnik')
    if(json){
      this.korisnik = JSON.parse(json)
      this.vlasnikId = this.korisnik.id
      console.log(this.vlasnikId)
    }
    
    this.ucitaj();
  }

  ucitaj() {
    this.http.get<any[]>(`http://localhost:8080/vikendice/vlasnik/${this.vlasnikId}`)
      .subscribe(data => this.vikendice = data);
  }

  onSlikaChange(event: any) {
    this.slika = event.target.files[0];
  }

  onJsonChange(event: any) {
    this.jsonFajl = event.target.files[0];
  }

  dodaj() {
  const formData = new FormData();

  // Obavezna polja - uvek string
  formData.append('naziv', this.nova.naziv || '');
  formData.append('mesto', this.nova.mesto || '');
  formData.append('opis', this.nova.opis || '');
  formData.append('cena', this.nova.cena != null ? this.nova.cena.toString() : '0');
  formData.append('broj_soba', this.nova.broj_soba != null ? this.nova.broj_soba.toString() : '0');
  //formData.append('telefon', this.nova.telefon || '');
  //formData.append('koordinate', this.nova.koordinate || '');
  formData.append('vlasnik_id', this.vlasnikId.toString());

  if (this.slika) formData.append('slika', this.slika);
  if (this.jsonFajl) formData.append('jsonFajl', this.jsonFajl);

  this.http.post('http://localhost:8080/vikendice/dodaj', formData)
    .subscribe({
      next: () => this.ucitaj(),
      error: err => console.error('Greška pri dodavanju:', err)
    });
}
  obrisi(id: number) {
    this.http.delete(`http://localhost:8080/vikendice/obrisi/${id}`)
      .subscribe(() => this.ucitaj());
  }

  pokreniIzmenu(v: any) {
    this.zaIzmenu = { ...v }; // kopija vikendice za izmenu
    this.prikaziFormuZaIzmenu = true;
  }

  sacuvajIzmenu() {
    this.http.put('http://localhost:8080/vikendice/izmeni', this.zaIzmenu)
      .subscribe(() => {
        this.prikaziFormuZaIzmenu = false;
        this.ucitaj();
      });
  }

  otkaziIzmenu() {
    this.prikaziFormuZaIzmenu = false;
  }
}