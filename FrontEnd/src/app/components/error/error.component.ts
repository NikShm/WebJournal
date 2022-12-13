import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['../../shared/css/center.css', './error.component.css']
})
export class ErrorComponent implements OnInit {
  code: string = this.router.url.substring(1);
  reason: string = '';
  description: string = '';

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.code === "404") {
      this.reason = "Page Not Found";
      this.description = "We're sorry, the page you requested doesn't exist or is unavailable";
    }
    else if (this.code === "403") {
      this.reason = "Forbidden";
      this.description = "We're sorry, the page you are trying to request has restricted access";
    }
  }

  goHome() {
    this.router.navigate(['']);
  }
}
