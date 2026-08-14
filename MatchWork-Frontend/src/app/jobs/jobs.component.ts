import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
  loading: boolean = false;

  constructor(
    private service: JobService, 
    private route: ActivatedRoute, 
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
      const term = params['search'] || '';
      this.searchTerm = term;
      this.loadJobs(term);
    });
  }

  loadJobs(term?: string) {
    this.loading = true;
    if(term) {
      this.service.searchByName(term).subscribe({
        next: (data) => {
          this.jobs = data.content;
          this.loading = false;
          console.log("Vagas encontrada!");
          this.cdr.markForCheck();
        },
        error: (erro) => {
          this.loading = false;
          console.error("Erro: ", erro);
          alert("Vaga não encontrada");
          this.cdr.markForCheck();
        }
      });
    } else {
      this.service.findAll().subscribe({
        next: (data) => {
          this.jobs = data.content;
          this.loading = false;
          console.log("Vagas carregadas!");
          this.cdr.markForCheck();
        },
        error: (erro) => {
          console.error("Erro: ", erro);
          alert("Erro ao carregar a lista de vagas!");
          this.loading = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  buscarVagaId(jobId: number) {
    this.router.navigate(['/job-open'], { queryParams: { jobOpen: jobId } });
  }
}