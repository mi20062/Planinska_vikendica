import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarOptions, EventClickArg } from '@fullcalendar/core';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import { RezervacijeServisService } from '../services/rezervacije-servis.service';
import { VikendiceService } from '../services/vikendice.service';
import { Rezervacija } from '../models/rezervacija';
import { Vikendica } from '../models/vikendica';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-vlasnik-rezervacije',
  standalone: true,
  imports: [CommonModule, FormsModule, FullCalendarModule, RouterLink],
  templateUrl: './vlasnik-rezervacije.component.html',
  styleUrls: ['./vlasnik-rezervacije.component.css']
})
export class VlasnikRezervacijeComponent implements OnInit {

  private rezervacijeServis = inject(RezervacijeServisService);
  private vikendiceServis = inject(VikendiceService);

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    plugins: [dayGridPlugin, interactionPlugin],
    events: [],
    eventClick: this.onEventClick.bind(this),
  };

  // ---- nova svojstva za HTML ----
  neobradjeneRezervacije: any[] = [];
  odbijModal: boolean = false;
  izabranaRezervacija: any = null;
  komentarOdbijanje: string = '';
  porukaOdbijanje: string = '';

  sveVikendice: Vikendica[] = [];
  aktuelneRezervacije: any[] = [];

  ngOnInit(): void {
    this.ucitajVikendice();
  }

  private ucitajVikendice(): void {
    this.vikendiceServis.getAll().subscribe(vikendice => {
      this.sveVikendice = vikendice;
      this.ucitajRezervacije();
    });
  }

  private ucitajRezervacije(): void {
    this.rezervacijeServis.getAll().subscribe((rez: Rezervacija[]) => {
      // mapiranje rezervacija sa nazivom vikendice
      this.aktuelneRezervacije = rez.map(r => {
        const vik = this.sveVikendice.find(v => v.id === r.vikendica_id);
        return {
          ...r,
          naziv: vik ? vik.naziv : 'Nepoznata vikendica'
        };
      });

      // filtriranje neobrađenih
      this.neobradjeneRezervacije = this.aktuelneRezervacije.filter(r => r.status === 'rezervisano');

      // priprema za FullCalendar
      this.calendarOptions.events = this.aktuelneRezervacije.map(r => ({
        title: r.naziv,
        start: r.datum_od,
        end: r.datum_do,
        className: r.status === 'rezervisano' ? 'otkazano' : 'zavrseno'
      }));
    });
  }

  // ---- metode za HTML ----
  potvrdi(r: any): void {
    this.rezervacijeServis.potvrdi(r.id).subscribe(() => this.ucitajRezervacije());
  }

  otvoriOdbijFormu(r: any): void {
    this.izabranaRezervacija = r;
    this.odbijModal = true;
    this.komentarOdbijanje = '';
    this.porukaOdbijanje = '';
  }

  zatvoriOdbijanje(): void {
    this.odbijModal = false;
    this.izabranaRezervacija = null;
    this.komentarOdbijanje = '';
    this.porukaOdbijanje = '';
  }

  odbij(): void {
    if (!this.komentarOdbijanje.trim()) {
      this.porukaOdbijanje = 'Морате написати коментар.';
      return;
    }
    if (!this.izabranaRezervacija) return;

    this.rezervacijeServis.odbij(this.izabranaRezervacija.id, this.komentarOdbijanje)
      .subscribe(() => {
        this.porukaOdbijanje = 'Резервација је одбијена.';
        this.ucitajRezervacije();
        this.zatvoriOdbijanje();
      });
  }

  onEventClick(info: EventClickArg): void {
    const event = info.event;
    alert(`Резервација за ${event.title}\nОд: ${event.startStr}\nДо: ${event.endStr}`);
  }
}
