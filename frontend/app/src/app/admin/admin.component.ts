import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {
  korisnici: any[] = [];
  zahtevi: any[] = [];
  vikendice: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.ucitajKorisnike();
    this.ucitajZahteve();
    this.ucitajVikendice();
  }

  ucitajKorisnike() {
    this.http.get<any[]>('http://localhost:8080/admin/korisnici')
      .subscribe(data => this.korisnici = data);
  }

  ucitajZahteve() {
    this.http.get<any[]>('http://localhost:8080/admin/zahtevi')
      .subscribe(data => this.zahtevi = data);
  }

  ucitajVikendice() {
    this.http.get<any[]>('http://localhost:8080/admin/vikendice')
      .subscribe(data => this.vikendice = data);
  }

  deaktiviraj(id: number) {
    this.http.put(`http://localhost:8080/admin/korisnik/deaktiviraj/${id}`, {})
      .subscribe(() => this.ucitajKorisnike());
  }

  obrisi(id: number) {
    this.http.delete(`http://localhost:8080/admin/korisnik/obrisi/${id}`)
      .subscribe(() => this.ucitajKorisnike());
  }

  prihvatiZahtev(id: number) {
    this.http.put(`http://localhost:8080/admin/zahtev/${id}/prihvati`, {})
      .subscribe(() => this.ucitajZahteve());
  }

  odbijZahtev(id: number) {
    this.http.put(`http://localhost:8080/admin/zahtev/${id}/odbij`, {})
      .subscribe(() => this.ucitajZahteve());
  }

  blokirajVikendicu(id: number) {
    /*this.http.put(`http://localhost:8080/admin/vikendica/blokiraj/${id}`, {})
      .subscribe(() => this.ucitajVikendice());*/
  }

  crvenaBoja(v: any): boolean {
    if(!v.ocene || v.ocene.length < 3) return false;
    return v.ocene.slice(-3).every((o: number) => o < 2);
  }
}
