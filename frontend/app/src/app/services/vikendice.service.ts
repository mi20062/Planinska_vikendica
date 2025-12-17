import { inject, Injectable } from '@angular/core';
import { Vikendica } from '../models/vikendica';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VikendiceService {
  private httpClient = inject(HttpClient)
  private backRoot = "http://localhost:8080/vikendice"
  constructor() { }
  
  getAll(){
    return this.httpClient.get<Vikendica[]>(`${this.backRoot}/getAll`)
  }
}
