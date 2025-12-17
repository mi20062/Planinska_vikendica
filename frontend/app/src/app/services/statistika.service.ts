import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Statistika } from '../models/statistika';

@Injectable({
  providedIn: 'root'
})
export class StatistikaService {
  private httpClient = inject(HttpClient)
  private backRoot = "http://localhost:8080/statistika"
  constructor() { }

  getAll(){
    return this.httpClient.get<Statistika>(`${this.backRoot}/getAll`)
  }
}
