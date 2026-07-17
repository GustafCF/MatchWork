import { ChangeDetectorRef, Component } from '@angular/core';
import { LoginService } from '../login.service'; 
import { LoginRequest } from './login.model';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {

  constructor(private service: LoginService, private cdr: ChangeDetectorRef, private router: Router) 
  {}

  loginRequest: LoginRequest = {
    email: '',
    password: ''
  };

  login() {
    this.service.login(this.loginRequest).subscribe({
      next: (response) => {
        console.log("Success!");
        alert("Login Successfully@!");
        localStorage.setItem("email", response.email);
        localStorage.setItem("token", response.accessToken);
        this.clearForm();
        this.router.navigate(['']);
      },
      error: (erro) => {
        console.log("Erro: ", erro);
        alert("Erro ao fazer login. Tente Novamente!");
      }
    });
  }

  onSubmit() {
    this.login();
  }

  clearForm() {
    this.loginRequest = {
      email: '',
      password: ''
    };
    this.cdr.detectChanges();
  }
}
