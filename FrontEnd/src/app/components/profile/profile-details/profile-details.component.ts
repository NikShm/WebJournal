import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Page } from 'src/app/models/pages';
import { Search } from 'src/app/models/search';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';
import { StorageService } from 'src/app/services/storage.service';

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

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private postService: PostService
  ) {}

  ngOnInit(): void {
    if (this.hasPermission()) {
      this.searchModel = new Search("createdAt", "DESC", 0, 6, {authorId: this.user.id, areApproved: true});
    }
    else {
      this.searchModel = new Search("publishedAt", "DESC", 0, 6, this.user.id);
    }
    this.postService.setSearchParameter(this.searchModel);
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
    this.postService.setSearchParameter(this.searchModel);
    this.search();
  }

  hasPermission() {
    const loggedUser = this.storageService.getUser();
    return loggedUser.role === "ADMIN" || loggedUser.role === "MODERATOR" || this.isYourProfile();
  }

  changePostsFilter() {
    this.postService.setSearchParameter(this.searchModel);
    this.search();
  }

  search() {
    if (this.hasPermission()) {
      this.postService.getFilteredAuthorsPostsPage(this.user.id).subscribe((page: Page) => this.articlesPage = page);
    }
    else {
      this.postService.getApprovedAuthorsPostsPage(this.user.id).subscribe((page: Page) => this.articlesPage = page);
    }
  }
}
