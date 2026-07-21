import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
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

  constructor(private router: Router) {}

  ngOnInit() {
    this.atualizarEstadoLogin();
  }

  toggleSidebar() {
    this.isSideBarOpen = !this.isSideBarOpen;
    if(this.isSideBarOpen) {
      this.atualizarEstadoLogin();
    }
  }

  atualizarEstadoLogin() {
    const token = localStorage.getItem('token');
    const email =  localStorage.getItem('email');
    this.isLoggedIn = !!token;
    this.loggedEmail = email;
  }

  logout() {
    localStorage.removeItem('email');
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.loggedEmail = null;
    this.router.navigate(['']);
  }
}