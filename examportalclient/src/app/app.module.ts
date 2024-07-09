import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/exam-portal-screens/login/login.component';
import { SignupComponent } from './components/exam-portal-screens/signup/signup.component';
import { MaterialModule } from './modules/material/material/material.module';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppConstants } from './config/AppConstants';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    SignupComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [HttpClient,AppConstants],
  bootstrap: [AppComponent]
})
export class AppModule { }
