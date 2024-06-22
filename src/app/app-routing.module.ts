import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/exam-portal-screens/signup/signup.component';
import { LoginComponent } from './components/exam-portal-screens/login/login.component';

const routes: Routes = [
  { path:'signup', component:SignupComponent, pathMatch:'full'},
  { path:'login', component:LoginComponent, pathMatch:'full'},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
