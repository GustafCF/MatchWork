import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JobResponseAll } from './job';
import { JobService } from '../job.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-jobs',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './jobs.html',
  styleUrl: './jobs.css',
})
export class JobsComponent implements OnInit {

  jobs: JobResponseAll[] = [];
  searchTerm: string = '';
  jobId: number = 0;

  constructor(private service: JobService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
      const term = params['search'] || '';
      this.searchTerm = term;
      this.loadJobs(term);
    });
  }

  loadJobs(term?: string) {
    if(term) {
      this.service.searchByName(term).subscribe({
        next: (data) => {
          this.jobs = data.content;
          console.log("Vagas encontrada!");
        },
        error: (erro) => {
          console.log("Erro: ", erro);
        }
      });
    } else {
      this.service.findAll().subscribe({
        next: (data) => {
          this.jobs = data.content;
          console.log("Vagas carregadas!");
        },
        error: (erro) => {
          console.log("Erro: ", erro);
          alert("Erro ao carregar a lista de vagas!");
        }
      });
    }
  }

  buscarVagaId(jobId: number) {
    this.router.navigate(['/job-open'], { queryParams: { jobOpen: jobId } });
  }

}