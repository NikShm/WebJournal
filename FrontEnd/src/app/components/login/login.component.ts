import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/login-request';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../shared/css/auth.css', './login.component.css']
})
export class LoginComponent implements OnInit {
  login: LoginRequest = new LoginRequest();
  loginFailed: boolean = false;
  errorMessage: string = '';

  constructor(
      private authService: AuthService,
      private storageService: StorageService,
      private router: Router
  ) {}

  ngOnInit(): void {
  }

  onLogin() {
    this.authService.login(this.login).subscribe({
      next: data => {
        this.storageService.saveUser(data);
        this.router.navigate(['/']);
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.loginFailed = true;
      }
    });
  }
}
