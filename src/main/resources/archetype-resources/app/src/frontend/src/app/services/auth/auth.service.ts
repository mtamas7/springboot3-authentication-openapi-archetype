import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticationRequest } from './authentication.request';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  endpoint: string = 'http://localhost:8080';
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  currentUser = {};

  authenticated$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.isLoggedIn);;
  
  constructor(private http: HttpClient, public router: Router) {

  }
  // Sign-in
  signIn(username: String, password: String): Observable<any> {

    let request = new AuthenticationRequest();
    request.email = username;
    request.password = password;
  
    return this.http
      .post<any>(`${this.endpoint}/authentication/api/v1/auth/token`, request)
  }
  getToken() {
    return localStorage.getItem('access_token');
  }

  isAuthenticated(): Observable<boolean> {
    return this.authenticated$.asObservable();
  }

  setAuthenticated(authenticated: boolean) {
    this.authenticated$.next(authenticated);
  }

  get isLoggedIn(): boolean {
    return this.getToken() !== null ? true : false;
  }

  doLogout() {
    let removeToken = localStorage.removeItem('access_token');
    if (removeToken == null) {
      this.setAuthenticated(false);
      this.router.navigate(['login']);
    }
  }

}