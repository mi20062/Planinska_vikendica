import { Component, inject, OnInit } from '@angular/core';
import { Korisnik } from '../models/korisnik';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { KorisniciService } from '../services/korisnici.service';

@Component({
  selector: 'app-korisnik',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './korisnik.component.html',
  styleUrl: './korisnik.component.css'
})
export class KorisnikComponent implements OnInit{
  private router = inject(Router)
  private korisnikServis = inject(KorisniciService)

  private sanitizer = inject(DomSanitizer)
  korisnik = new Korisnik

  novoIme = ""
  novoPrezime = ""
  novaAdresa = ""
  noviEmail =""
  noviKontakt_telefon = ""
  noviBroj_kreditne_kartice = ""
  profileImgUrl!: SafeUrl;
  poruka = ""
  poruka_slika = ""
  nova_profilna = ""
  data = new FormData()

  ngOnInit(): void {
    /*let kor = localStorage.getItem("korisnik")
    if(kor){
      this.korisnik = JSON.parse(kor)
    }*/
    let json = sessionStorage.getItem("korisnik")
    if(json){
      this.korisnik = JSON.parse(json)
      
    }
    
    
    if (this.korisnik.profilna_slika) {
      this.profileImgUrl = this.sanitizer.bypassSecurityTrustUrl(
        `data:image/png;base64,${this.korisnik.profilna_slika}`
      )
    } else {
      // Ako nema, koristi statičku iz assets foldera
      //this.convertImageToBase64('assets/planinaAkvarel.jpg');
    }
    
  }

  convertImageToBase64(path: string) {
    fetch(path)
      .then(response => response.blob())
      .then(blob => {
        const reader = new FileReader();
        reader.onloadend = () => {
          const base64data = reader.result as string;
          this.profileImgUrl = this.sanitizer.bypassSecurityTrustUrl(base64data);
          console.log('Slika konvertovana u Base64 (primer):', base64data.substring(0, 80) + '...');
        };
        reader.readAsDataURL(blob);
      })
      .catch(err => console.error('Greška pri učitavanju slike:', err));
  }

  logout(){
    localStorage.removeItem('korisnik')
    this.router.navigate([''])
  }
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
      this.nova_profilna =  URL.createObjectURL(file)
      this.data.append("profilna_slika", file, file.name)
      
    }

  }
  azuriraj(){
    if(!this.proveriKarticu())
      return
    if(!this.proveriTelefon())
      return
    if(this.novoIme == ""){
      this.poruka = "Ime je obavezno"
      return
    }
    if(this.novoPrezime == ""){
      this.poruka = 'Prezime je obavezno'
      return
    }
    if(this.noviEmail == ''){
      this.poruka = 'Mejl adresa je obavezna'
      return
    }

    if(this.noviBroj_kreditne_kartice == ''){
      this.poruka = 'Broj kartice je obavezan'
      return
    }
    if(this.novaAdresa == ""){
      this.poruka = 'Adresa je obavezna'
      return
    }
    if(this.noviKontakt_telefon == ''){
      this.poruka = 'Telefon je obavezan'
      return
    }
    

    Object.entries(this.korisnik).forEach(([key, value]) => {
      if (value !== null && value !== undefined) {
        if(key != 'profile_img'&& key!= 'datum_registracije')         
          this.data.append(key, value.toString())
      }
    })
    
    this.data.set("ime", this.novoIme);
    this.data.set("prezime", this.novoPrezime);
    this.data.set("adresa", this.novaAdresa);
    this.data.set("email", this.noviEmail);
    this.data.set("kontakt_telefon", this.noviKontakt_telefon);
    this.data.set("broj_kreditne_kartice", this.noviBroj_kreditne_kartice);
    


    this.korisnikServis.azuriraj(this.data).subscribe(data => {
      if(data){
        this.korisnik = data
        sessionStorage.setItem('korisnik', JSON.stringify(this.korisnik))
        
        this.ngOnInit()
      }else
        this.poruka ="Greska prlikom izmena"
    })

    
  }

  proveriKarticu(){

    // očisti razmake
    this.noviBroj_kreditne_kartice = this.noviBroj_kreditne_kartice.replace(/\s/g, '');

    // regex: Diners (15), Mastercard (16), Visa (16) — ispravljeni Visa deo (\d{12})
    const cardRe = /^(?:(?<Diners>3(?:0[0-3]|6|8)\d{12})|(?<Mastercard>5[1-5]\d{14})|(?<Visa>4(?:539|556|916|532|485|716|929)\d{12}))$/;

    const m = this.noviBroj_kreditne_kartice.match(cardRe);
    if (!m) {
      this.poruka = "Neispravan broj kreditne kartice!"
      return false;
    }
    return true;
  }

  proveriTelefon(){

    this.noviKontakt_telefon = this.noviKontakt_telefon.replace(/\s/g, '')

    if (this.noviKontakt_telefon === '') {
      this.poruka = 'Broj telefona je obavezan'
      return false
    }

    // jedinstveni regex koji pokriva i mobilne i fiksne brojeve
    const phoneRegex = /^(6\d{7,8}|[1-5]\d{7,8})$/

    if (!phoneRegex.test(this.noviKontakt_telefon)) {
      this.poruka = 'Broj telefona nije validan (mora imati 8 ili 9 cifara i početi sa 6 ili od 1 do 5)'
      return false
    }

    this.poruka = ''
    return true
  }

}
