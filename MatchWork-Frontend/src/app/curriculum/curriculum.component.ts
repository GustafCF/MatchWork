import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CurriculumService } from '../curriculum.service';
import { UserService } from '../user.service';
import { Curriculum, UserResponseDto } from '../cadastro/user.model';
import { CurriculumRequestDto } from './curriculum.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-curriculum',
  imports: [FormsModule, CommonModule],
  templateUrl: './curriculum.html',
  styleUrl: './curriculum.css',
})
export class CurriculumComponent implements OnInit {

  showEditBtn: Boolean = false;

  curriculumForm: Curriculum = {
    education: [],
    experience: [],
    skills: []
  }

  user: UserResponseDto = {
    name: "",
    age: 0,
    addres: "",
    cpf: "",
    email: "",
    curriculum: this.curriculumForm
  };

  request: CurriculumRequestDto = {
    education: "",
    experience: "",
    skills: ""
  };

  constructor(private service: CurriculumService, private userService: UserService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadCurriculum();
  }

  loadCurriculum() {
    this.userService.findByEmail().subscribe({
      next: (response) => {
        console.log("success!", response);
        this.user = response;
        this.cdr.markForCheck();
      },
      error: (erro) => {
        console.log("Erro: ", erro);
        this.cdr.markForCheck();
      }
    });
  }

  createCurriculum() {
    this.service.insert(this.request).subscribe({
      next: (response) => {
        console.log("success!", response);
        this.user.curriculum = response;
        this.showEditBtn = false;
        this.clearForm();
        this.cdr.markForCheck();
      },
      error: (erro) => {
        alert("Erro ao criar currículo");
        console.log("Erro ao criar currículo", erro);
      }
    });
  }

  showEditButton() {
    this.showEditBtn = true;
  }

  clearForm() {
    this.request = {
      education: "",
      experience: "",
      skills: ""
    };
    this.cdr.detectChanges();
  }

  clear() {
    this.clearForm();
  }

  cancelForm() {
    this.showEditBtn = false;
    this.cdr.markForCheck();
  }

  cancel() {
    this.cancelForm();
  }

}