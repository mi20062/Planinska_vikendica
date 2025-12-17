import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { KorisniciService } from '../services/korisnici.service';
import { Korisnik } from '../models/korisnik';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private ruter = inject(Router)
  private korisniciServis = inject(KorisniciService)
    
  korisnicko_ime = ""
  lozinka = ""
  tip = ""
  poruka = ""
  korisnik = new Korisnik()

  login(){
    this.poruka = ""
    if(this.korisnicko_ime == ""){
      this.poruka = "Morate uneti Korisnicko ime"
      return
    }
    if(this.lozinka == ""){
      this.poruka = "Morate uneti lozinku"
      return
    }

    if(this.tip == ""){
      this.poruka = "Morate odabrati tip"
      return
    }

    this.korisniciServis.login(this.korisnicko_ime, this.lozinka).subscribe(data =>{
      if(data){
        this.korisnik = data
        if(this.korisnik.uloga == "admin"){
          alert('Nismo sigurni da li ste vi admin')
          return
        }
        sessionStorage.setItem("korisnik", JSON.stringify(this.korisnik))

        this.ruter.navigate(['/korisnik'])
      }else{
        this.poruka = "Neuspesno! Probajte ponovo."
      }
      
    })


  }



}
