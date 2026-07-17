import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JobResponseAll } from './job';
import { JobService } from '../job.service';

@Component({
  selector: 'app-jobs',
  imports: [CommonModule, FormsModule],
  templateUrl: './jobs.html',
  styleUrl: './jobs.css',
})
export class JobsComponent implements OnInit {

  jobs: JobResponseAll[] = [];

  constructor(private service: JobService) {}

  ngOnInit(): void {
    this.loadJobs()
  }

  loadJobs() {
    this.service.findAll().subscribe({
      next: (data) => {
        this.jobs = data.content;
        console.log("Success!");
        alert("Success!")
      },
      error: (erro) => {
        console.log("Erro: ", erro);
        alert("Erro ao carregar a lista de vagas!");
      }
    })
  }
}