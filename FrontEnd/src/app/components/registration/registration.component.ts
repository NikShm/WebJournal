import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../../shared/css/auth.css', './registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  showPlaceholder(event: any) {
    this.sleep(250).then(() => event.target.type = 'date');
  }

  removePlaceholder(event: any) {
    if (event.target.value === "") {
      event.target.type = 'text';
    }
  }

  private sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}
