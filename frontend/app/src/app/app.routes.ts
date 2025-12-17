import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { PocetnaComponent } from './pocetna/pocetna.component';
import { KorisnikComponent } from './korisnik/korisnik.component';
import { RegistracijaComponent } from './registracija/registracija.component';
import { VikendiceTuristaComponent } from './vikendice-turista/vikendice-turista.component';
import { DetaljiVikendiceComponent } from './detalji-vikendice/detalji-vikendice.component';
import { TuristaRezervacijeComponent } from './turista-rezervacije/turista-rezervacije.component';
import { VlasnikRezervacijeComponent } from './vlasnik-rezervacije/vlasnik-rezervacije.component';
import { StatistikaComponent } from './statistika/statistika.component';
import { VlasnikVikendiceComponent } from './vlasnik-vikendice/vlasnik-vikendice.component';
import { AdminComponent } from './admin/admin.component';

export const routes: Routes = [
    {path: '', component: PocetnaComponent},
    {path: 'login', component: LoginComponent},
    {path: 'korisnik', component: KorisnikComponent},
    {path: 'registracija', component: RegistracijaComponent},
    {path: 'vikendiceTurista', component: VikendiceTuristaComponent},
    {path: 'detaljiVikendice/:id', component: DetaljiVikendiceComponent},
    {path: 'turistaRezervacije', component: TuristaRezervacijeComponent},
    {path: 'vlasnikRezervacije', component: VlasnikRezervacijeComponent},
    { path: 'statistika', component: StatistikaComponent },
    { path: 'vlasnikVikendice', component: VlasnikVikendiceComponent },
    { path: 'admin', component: AdminComponent }

   
];
