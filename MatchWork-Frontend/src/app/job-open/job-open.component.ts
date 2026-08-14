import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { JobService } from '../job.service';
import { JobResponse } from '../jobs/job';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-job-open',
  imports: [CommonModule, RouterLink],
  templateUrl: './job-open.html',
  styleUrl: './job-open.css',
})
export class JobOpenComponent implements OnInit {
  
  job: JobResponse = {
    id: 0,
    name: '',
    description: '',
    responsibility: '',
    requirements: '',
    additionalInfo: '',
    steps: [],
    jobModel: [],
    typeContract: [],
    enterprise: [],
    date: '',
  };

  constructor(
    private route: ActivatedRoute,
    private service: JobService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const id = params['jobOpen'];
        this.loadJobId(id);
    });
  }

  loadJobId(jobId: number) {
    console.log(jobId);
    this.service.findById(jobId).subscribe({
      next: (response) => {
        this.job = response;
        console.log("Success: ", response);
        this.cdr.markForCheck();
      },
      error: (erro) => {
        console.error("Erro:", erro);
        alert("Erro ao carregar Vaga!");
        this.cdr.markForCheck();
      }
    });
  }
}