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
                path: 'cadastro',
                loadComponent: () => import('./cadastro/cadastro.component').then(m => m.CadastroComponent)
            }
        ]
    }
];
