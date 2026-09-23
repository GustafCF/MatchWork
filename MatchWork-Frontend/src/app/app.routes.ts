import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            {
                path: '',
                loadComponent: () => import('./landingpage/landingpage.component').then(m => m.LandingpageComponent)
            },
            {
                path: 'login',
                loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)
            },
            {
                path: 'cadastro',
                loadComponent: () => import('./cadastro/cadastro.component').then(m => m.CadastroComponent)
            },
            {
                path: 'jobs',
                loadComponent: () => import('./jobs/jobs.component').then(m => m.JobsComponent)
            },
            {
                path: 'job-open',
                loadComponent: () => import('./job-open/job-open.component').then(m => m.JobOpenComponent)
            },
            {
                path: 'curriculum',
                loadComponent: () => import('./curriculum/curriculum.component').then(m => m.CurriculumComponent)
            }
        ]
    }
];