import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Rezervacija } from '../models/rezervacija';

@Injectable({
  providedIn: 'root'
})
export class RezervacijeServisService {
  private httpClient = inject(HttpClient)
  private backRoot = "http://localhost:8080/rezervacije"

  constructor() { }


  posaljiRezervaciju(r: Rezervacija){
    const data = {
      id: r.id,
      vikendica_id: r.vikendica_id,
      turista_id: r.turista_id,
      datum_od: r.datum_od,
      datum_do: r.datum_do,
      
      status: r.status
    }
    return this.httpClient.post<Rezervacija>(`${this.backRoot}/posaljiRezervaciju`, data)
  }

  getAll(){
      return this.httpClient.get<Rezervacija[]>(`${this.backRoot}/getAll`)
  }

  potvrdi(id: number) {
  return this.httpClient.put(`${this.backRoot}/${id}/potvrdi`, {});
  }

  odbij(id: number, komentar: string) {
    return this.httpClient.put(`${this.backRoot}/${id}/odbij`, { komentar });
  }

  getMesecne(vlasnikId: number) {
  return this.httpClient.get<any>(`${this.backRoot}/statistika/meseci/${vlasnikId}`);
  }

  getVikendVsRadni(vlasnikId: number) {
    return this.httpClient.get<any>(`${this.backRoot}/statistika/vikendi/${vlasnikId}`);
  }

  otkazi(id: number) {
  return this.httpClient.put(`${this.backRoot}/${id}/otkazi`, {});
  }

}
