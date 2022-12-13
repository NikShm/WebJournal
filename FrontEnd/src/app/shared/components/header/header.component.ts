import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { StorageService } from 'src/app/services/storage.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
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
            this.router.navigate(['/authors/' + this.storageService.getUser().id]);
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
