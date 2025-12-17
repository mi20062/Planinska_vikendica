import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Korisnik } from '../models/korisnik';

@Injectable({
  providedIn: 'root'
})
export class KorisniciService {
   private httpClient = inject(HttpClient)
  private backRoot = "http://localhost:8080/korisnici"
  constructor() { }

  login(korisnicko_ime:String, lozinka: String){
    const data = {
      korisnicko_ime: korisnicko_ime,
      lozinka: lozinka
    }
    return this.httpClient.post<Korisnik>(`${this.backRoot}/login`, data)
  }

  azuriraj(data: FormData){
    return this.httpClient.post<Korisnik>(`${this.backRoot}/azuriraj`, data)
  }

  registruj(data: FormData){
    return this.httpClient.post<Korisnik>(`${this.backRoot}/registruj`, data)
  }

}
