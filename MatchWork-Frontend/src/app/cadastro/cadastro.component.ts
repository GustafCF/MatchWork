import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import { UserRequestDto } from './user.model';
import { UserService } from '../user.service';
import { FormsModule } from '@angular/forms';
import { EnterpriseRequestDto } from './enterprise.model';
import { EnterpriseService } from '../enterprise.service';

@Component({
  selector: 'app-cadastro',
  imports: [RouterModule, CommonModule, NgxMaskDirective, FormsModule],
  providers: [provideNgxMask()],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css',
})
export class CadastroComponent {
  showUserForm: boolean = true;
  showCompanyForm: boolean = false;

  user: UserRequestDto = {
    name: '',
    age: 0,
    addres: '',
    cpf: '',
    email: ''
  };

  enterprise: EnterpriseRequestDto = {
    name: '',
    description: '',
    location: '',
    cnpj: '',
    poBox: ''
  };

  constructor(
    private userService: UserService,
    private enterpriseService: EnterpriseService,
    private cdr: ChangeDetectorRef
  ) {}

  showUserOnly() {
    this.showUserForm = true;
    this.showCompanyForm = false;
  }

  showCompanyOnly() {
    this.showUserForm = false;
    this.showCompanyForm = true;
  }

  onSubmit() {
    if(this.showUserForm) {
      this.saveUser();
    } else if(this.showCompanyForm) {
      this.saveEnterprise();
    }
  }

  saveUser() {
    this.userService.insert(this.user).subscribe({
      next: (response) => {
        console.log("Usuário salvo com sucesso!", response);
        alert('Usuário cadastrado com sucesso!');
        this.clearUserForm();
      },
      error: (erro) => {
        console.log("Erro ao salvar usuário!", erro);
        alert("Erro ao cadastrar usuário. Tente novamente");
      }
    });
  }

  saveEnterprise() {
    this.enterpriseService.insert(this.enterprise).subscribe({
      next: (response) => {
        console.log("Empresa Cadstrada com sucesso!", response);
        alert('Empresa cadastrada com sucesso!');
        this.clearEnterpriseForm();
      },
      error: (erro) => {
        console.log("Erro ao salvar Empresa!", erro);
        alert('Erro ao cadastrar empresa! Tente novamente.');
      }
    })
  }

  clearEnterpriseForm() {
    this.enterprise = {
      name: '',
      description: '',
      location: '',
      cnpj: '',
      poBox: ''
    };
    this.cdr.detectChanges();
  }

  clearUserForm() {
    this.user = {
      name: '',
      age: 0,
      addres: '',
      cpf: '',
      email: ''
    };
    this.cdr.detectChanges();
  }

  clearForms() {
    this.clearUserForm();
    this.clearEnterpriseForm();
  }
}