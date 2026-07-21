import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobService } from '../job.service';
import { JobResponse } from '../jobs/job';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-job-open',
  imports: [CommonModule],
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
    ProcessSteps: [],
    jobModel: [],
    typeContract: [],
    enterprise: [],
    date: '',
  };

  constructor(private service: JobService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const id = params['jobOpen'];
      this.loadJobId(id);
    });
  }

  loadJobId(jobId: number) {
    this.service.findByid(jobId).subscribe({
      next: (response) => {
        this.job = response;
        console.log(response);
      },
      error: (erro) => {
        console.log("Erro:", erro);
      }
    });
  }
}
