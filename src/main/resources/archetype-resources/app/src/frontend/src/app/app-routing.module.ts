import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import { HomeComponent } from './components/home/home.component';
//import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { AuthGuard } from "./services/auth/auth.guard";
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: SigninComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: '**', component: NotFoundComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }