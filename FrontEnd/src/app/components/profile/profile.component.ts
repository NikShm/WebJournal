import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user!: User;
  private username: string = this.route.snapshot.paramMap.get('username') ?? '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private storageService: StorageService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userService.getUser(this.username).subscribe(data => this.user = new User(data));
  }

  isYourProfile(): boolean {
    return this.username === this.storageService.getUser().username;
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
          this.storageService.clear();
          window.location.reload();
      },
      error: err => {
          console.error(err);
      }
    });
  }
}
