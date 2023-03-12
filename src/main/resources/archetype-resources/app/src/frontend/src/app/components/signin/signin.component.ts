import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css'],
})
export class SigninComponent implements OnInit {
  username!: string;
  password!: string;

  constructor(
    public authService: AuthService,
    public snackbarService: SnackbarService,
    public router: Router
  ) {}

  ngOnInit() { 

    if(this.authService.isLoggedIn) {
      this.router.navigate(['home']);
    }

  }

  login(): void {

    this.authService.signIn(this.username, this.password)
    .subscribe({
      next: (res) => {
        localStorage.setItem('access_token', res.token);
        this.router.navigate(['home']);
        this.authService.setAuthenticated(true)
      },
      error: (e) => {
        console.error(e)
        this.snackbarService.showErrorSnackBarWithMessage("Something went wrong while logging in.. Try again!");
        this.username = '';
        this.password = '';
      }

    });

  }
}