import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-landingpage',
  imports: [RouterModule, CommonModule],
  templateUrl: './landingpage.html',
  styleUrl: './landingpage.css',
})
export class LandingpageComponent {}
