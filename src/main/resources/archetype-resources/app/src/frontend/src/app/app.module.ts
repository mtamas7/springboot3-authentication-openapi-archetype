import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SigninComponent } from './components/signin/signin.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './services/auth/authconfig.interceptor';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HomeComponent } from './components/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { NotFoundComponent } from './components/not-found/not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    HomeComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}