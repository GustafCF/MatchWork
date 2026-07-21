import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-landingpage',
  imports: [RouterModule, CommonModule],
  templateUrl: './landingpage.html',
  styleUrl: './landingpage.css',
})
export class LandingpageComponent {

  constructor(private router: Router) 
  {}

  buscarVaga(termo: String) {
    this.router.navigate(['/jobs'], { queryParams: { search: termo.trim() } });
  }
}
