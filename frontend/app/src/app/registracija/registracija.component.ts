import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SafeUrl } from '@angular/platform-browser';
import { KorisniciService } from '../services/korisnici.service';
import { Router, RouterLink } from '@angular/router';
import { Korisnik } from '../models/korisnik';

@Component({
  selector: 'app-registracija',
  standalone: true,
  imports: [FormsModule,RouterLink],
  templateUrl: './registracija.component.html',
  styleUrl: './registracija.component.css'
})
export class RegistracijaComponent {
  private korisnikServis = inject(KorisniciService)
  private router = inject(Router)
  korisnik = new Korisnik()

  
  profilna_slika = ""
  
  poruka_slika = ""
  poruka = ""
  profileImgUrl!: SafeUrl;
  data = new FormData()

  
  onProfileImageChange(event: Event){
    const file = (event.target as HTMLInputElement).files?.[0];
    console.log('getting file ', file?.name)
    if (file) {
      if(file.type != 'image/jpeg' && file.type != 'image/png'){
        this.poruka_slika = 'Slika moze biti samo jpeg ili png formata'
        return
      }

      const img = new Image();
      img.onload = () => {
        if (img.width < 100 || img.width > 300 || img.height < 100 || img.height > 300) {
          this.poruka_slika = 'Slika mora biti između 100x100 i 300x300 piksela';
          return;
        }
      }
      this.profilna_slika =  URL.createObjectURL(file)
      this.data.append("profilna_slika", file, file.name)
    }
  }

  registruj(){
    this.data = new FormData();
    if(!this.proveriKarticu())
      return
    if(!this.proveriTelefon())
      return
    if(this.korisnik.ime == ""){
      this.poruka = "Ime je obavezno"
      return
    }
    if(this.korisnik.prezime == ""){
      this.poruka = 'Prezime je obavezno'
      return
    }
    if(this.korisnik.email == ''){
      this.poruka = 'Mejl adresa je obavezna'
      return
    }

    if(this.korisnik.broj_kreditne_kartice == ''){
      this.poruka = 'Broj kartice je obavezan'
      return
    }
    if(this.korisnik.adresa == ""){
      this.poruka = 'Adresa je obavezna'
      return
    }
    if(this.korisnik.kontakt_telefon == ''){
      this.poruka = 'Unesite telefon!'
      return
    }
    if(this.korisnik.pol == ''){
      this.poruka = 'Unesite pol!'
      return
    }
    if(this.korisnik.lozinka == ''){
      this.poruka = 'Unesite lozinku!'
      return
    }
    if(this.korisnik.uloga == ''){
      this.poruka = 'Unesite ulogu!'
      return
    }
    if(this.korisnik.korisnicko_ime==''){
      this.poruka = 'Unesite korisnicko ime!'
      return
    }

    Object.entries(this.korisnik).forEach(([key, value]) => {
      if (value !== null && value !== undefined) {
        if(key != 'profilna_slika')         
          this.data.append(key, value.toString())
        console.log(key, value)
      }
    })
    


    this.korisnikServis.registruj(this.data).subscribe(data => {
      if(data){
        this.router.navigate([''])
      }else
        this.poruka ="Greska prlikom registracije"
    })

    

  }

  proveriKarticu(){

    // očisti razmake
    this.korisnik.broj_kreditne_kartice = this.korisnik.broj_kreditne_kartice.replace(/\s/g, '');

    // regex: Diners (15), Mastercard (16), Visa (16) — ispravljeni Visa deo (\d{12})
    const cardRe = /^(?:(?<Diners>3(?:0[0-3]|6|8)\d{12})|(?<Mastercard>5[1-5]\d{14})|(?<Visa>4(?:539|556|916|532|485|716|929)\d{12}))$/;

    const m = this.korisnik.broj_kreditne_kartice.match(cardRe);
    if (!m) {
      this.poruka = "Neispravan broj kreditne kartice!"
      return false;
    }
    return true;
  }

  proveriTelefon(){

    this.korisnik.kontakt_telefon = this.korisnik.kontakt_telefon.replace(/\s/g, '')

    if (this.korisnik.kontakt_telefon === '') {
      this.poruka = 'Broj telefona je obavezan'
      return false
    }

    // jedinstveni regex koji pokriva i mobilne i fiksne brojeve
    const phoneRegex = /^(6\d{7,8}|[1-5]\d{7,8})$/

    if (!phoneRegex.test(this.korisnik.kontakt_telefon)) {
      this.poruka = 'Broj telefona nije validan (mora imati 8 ili 9 cifara i početi sa 6 ili od 1 do 5)'
      return false
    }

    this.poruka = ''
    return true
  }



}