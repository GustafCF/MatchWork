import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class LayoutComponent implements OnInit {

  isSideBarOpen = false;
  loggedEmail: string | null = null;
  isLoggedIn = false;

  constructor(
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    this.atualizarEstadoLogin();
  }

  atualizarEstadoLogin() {
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      const email = localStorage.getItem('email');
      this.isLoggedIn = !!token;
      this.loggedEmail = email;
    } else {
      this.isLoggedIn = false;
      this.loggedEmail = null;
    }
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('email');
      localStorage.removeItem('token');
    }
    this.isLoggedIn = false;
    this.loggedEmail = null;
    this.router.navigate(['']);
  }

  toggleSidebar() {
    this.isSideBarOpen = !this.isSideBarOpen;
    if(this.isSideBarOpen) {
      this.atualizarEstadoLogin();
    }
  }
}