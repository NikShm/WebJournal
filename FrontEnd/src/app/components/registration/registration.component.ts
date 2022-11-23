import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequest } from 'src/app/models/registration-request';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../../shared/css/auth.css', './registration.component.css']
})
export class RegistrationComponent implements OnInit {
  reg: RegistrationRequest = new RegistrationRequest();
  regFailed: boolean = false;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
  }

  showPlaceholder(event: any) {
    this.sleep(250).then(() => event.target.type = 'date');
    this.LabelUp(event);
  }

  removePlaceholder(event: any) {
    if (event.target.value === "") {
      event.target.type = 'text';
      this.LabelDown(event);
    }
  }

  private sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  onRegistration() {
    this.authService.register(this.reg).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.regFailed = true;
      }
    });
  }

  LabelUp(event: any) {
    event.target.nextElementSibling.classList.add('label-up');
  }

  LabelDown(event: any) {
    if (event.target.value === "") {
      event.target.nextElementSibling.classList.remove('label-up');
    }
  }
}
