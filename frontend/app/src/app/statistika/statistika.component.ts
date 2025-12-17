import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';
import { RezervacijeServisService } from '../services/rezervacije-servis.service';
import { RouterLink } from '@angular/router';

Chart.register(...registerables);

@Component({
  selector: 'app-statistika',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './statistika.component.html',
  styleUrls: ['./statistika.component.css']
})
export class StatistikaComponent implements OnInit {
  private rezervacijeServis = inject(RezervacijeServisService);

  ngOnInit(): void {
    this.ucitajMesecne();
    this.ucitajVikendVsRadni();
  }

  private ucitajMesecne() {
    this.rezervacijeServis.getMesecne(1).subscribe(data => {
      const labels = Array.from({ length: 12 }, (_, i) => `${i + 1}. месец`);
      const datasets = Object.entries(data).map(([vik, meseci]: any) => ({
        label: vik,
        data: labels.map((_, i) => meseci[i + 1] || 0),
        backgroundColor: this.randomColor()
      }));

      new Chart('barChart', {
        type: 'bar',
        data: { labels, datasets },
        options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
      });
    });
  }

  private ucitajVikendVsRadni() {
    this.rezervacijeServis.getVikendVsRadni(1).subscribe(data => {
      const labels = Object.keys(data);
      const datasets = labels.map(vik => ({
        label: vik,
        data: [data[vik].vikend, data[vik].radni],
        backgroundColor: [this.randomColor(), this.randomColor()]
      }));

      new Chart('pieChart', {
        type: 'pie',
        data: {
          labels: labels.map(v => `${v} (викенд/радни)`),
          datasets: [{
            data: labels.map(l => data[l].vikend + data[l].radni),
            backgroundColor: labels.map(() => this.randomColor())
          }]
        },
        options: { responsive: true }
      });
    });
  }

  private randomColor() {
    return `rgb(${Math.random()*255}, ${Math.random()*255}, ${Math.random()*255})`;
  }
}
