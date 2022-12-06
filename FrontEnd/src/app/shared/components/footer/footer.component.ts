import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {StorageService} from "../../../services/storage.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor(
    private router: Router,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
  }

  goToProfile() {
    if (!this.storageService.isLoggedIn()) {
      this.router.navigate(['login']);
    }
    else {
      this.router.navigate(['/authors/' + this.storageService.getUser().username]);
    }
  }

  goToNewsPosts() {
    if (!this.storageService.isLoggedIn()) {
      this.router.navigate(['login']);
    }
    else {
      this.router.navigate(['/news-posts']);
    }
  }

  goToCreatePostPage() {
    if (!this.storageService.isLoggedIn()) {
      this.router.navigate(['login']);
    }
    else {
      this.router.navigate(['/createPost']);
    }
  }
}
