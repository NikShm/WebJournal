import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Author} from "../../models/author";
import {UserService} from "../../services/user.service";
import {PostService} from "../../services/post.service";
import {PostList} from "../../models/postList";
import {catchError} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  userImage: string = "assets/UsersIcon/user_";
  postImage: string = "assets/PostImage/post_";
  authors: Author[] = [];
  posts: PostList[] = [];
  isDataLoaded: boolean = false;

  constructor(private userService: UserService,
              private postService: PostService,
              private router: Router) {
  }

  ngOnInit() {
    this.userService.getFavoriteAuthors().subscribe((data: any) => {this.authors=data;})
    this.postService.getToPerMonth().subscribe((data: any) => {this.posts = data; this.isDataLoaded = true});
  }

  onErrorUserImage(event:any) {
    event.target.src = 'assets/iconsAccount.png';
  }

  onErrorPostImage(event:any) {
    event.target.src = 'assets/PostImage/default.png';
  }
}
