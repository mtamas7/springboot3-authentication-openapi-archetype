import { Component } from '@angular/core';
import { AuthService } from './services/auth/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(public authService: AuthService) {
  }
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  ngOnInit(): void {
    this.authService.isAuthenticated().subscribe((isAuthenticated: boolean) => {
      this.isLoggedIn = isAuthenticated;
    });
  }

  logout() {
    this.authService.doLogout();
  }
}