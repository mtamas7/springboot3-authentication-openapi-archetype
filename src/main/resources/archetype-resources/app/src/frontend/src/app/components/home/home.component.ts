import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: Object = {};
  constructor(
    public authService: AuthService,
    private actRoute: ActivatedRoute
  ) {

  }
  ngOnInit() {}
}