import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Page } from 'src/app/models/pages';
import { Search } from 'src/app/models/search';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['../profile.component.css', './profile-details.component.css']
})
export class ProfileDetailsComponent implements OnInit {
  @Input() user!: User;
  @Output() onEdit = new EventEmitter<boolean>();
  articlesPage: Page = new Page([], 0);
  searchModel!: Search;
  role: string = '';

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private postService: PostService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = this.user.role;
    if (this.hasPermissionToFilter()) {
      this.searchModel = new Search("createdAt", "DESC", 0, 6, {areApproved: true});
    }
    else {
      this.searchModel = new Search("publishedAt", "DESC", 0, 6, {areApproved: true});
    }
    this.postService.setSearchProfileParameter(this.searchModel);
    this.search();
  }

  ngAfterViewInit() {
    document.getElementById('footer')?.classList.add('footer-margin');
  }

  enableEditMode() {
    this.onEdit.emit(true);
  }

  isYourProfile(): boolean {
    return this.storageService.getUser().id === this.user.id;
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

  onErrorUserImage(event:any) {
    event.target.src = 'assets/UsersIcon/default.png';
  }

  onErrorPostImage(event: any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  changePage(page: number) {
    this.searchModel.page = page
    this.postService.setSearchProfileParameter(this.searchModel);
    this.search();
  }

  isAdmin() {
    return this.storageService.getUser().role === "ADMIN";
  }

  hasPermissionToFilter() {
    const loggedUser = this.storageService.getUser();
    return this.isAdmin() || loggedUser.role === "MODERATOR" || this.isYourProfile();
  }

  hasPermissionToChangeRole() {
    return this.isAdmin() && this.user.role !== "ADMIN";
  }

  changePostsFilter() {
    this.postService.setSearchProfileParameter(this.searchModel);
    this.search();
  }

  changeRole() {
    if (this.user.role !== this.role) {
      if (window.confirm(`Change ${this.user.username}'s role to ${this.role}?`)) {
        this.userService.changeUserRole(this.user.id, this.role).subscribe();
      }
      else {
        this.role = this.user.role;
      }
      window.location.reload();
    }
  }

  search() {
    if (this.hasPermissionToFilter()) {
      this.postService.getFilteredAuthorsPostsPage(this.user.id).subscribe((page: Page) => this.articlesPage = page);
    }
    else {
      this.postService.getApprovedAuthorsPostsPage(this.user.id).subscribe((page: Page) => this.articlesPage = page);
    }
  }

  deleteAccount() {
    if (window.confirm("Are you sure you want to delete this account? This action cannot be undone")) {
      this.userService.deleteUserAccount(this.user.id).subscribe(() => {
        this.router.navigate(['/login']);
      });
    }
  }

  follow() {
    this.userService.followUser(this.storageService.getUser().id, this.user.id).subscribe(() => {
      window.alert("Now you follow " + this.user.username);
      window.location.reload();
    });
  }

  unfollow() {
    this.userService.unfollowUser(this.storageService.getUser().id, this.user.id).subscribe(() => {
      window.alert("You unfollowed " + this.user.username);
      window.location.reload();
    });
  }
}
