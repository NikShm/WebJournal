import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  form: any = {
    username: null,
    email: null,
    password: null,
    birthDate: null,
    bio: null
  }
  isSuccessful = false;
  isRegistrationFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const {username, email, password, birthDate, bio} = this.form;

    this.authService.register(username, email, password, birthDate, bio).subscribe({
      next: () => {
        this.isSuccessful = true;
        this.isRegistrationFailed = false;
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isRegistrationFailed = true;
      }
    });
  }
}
