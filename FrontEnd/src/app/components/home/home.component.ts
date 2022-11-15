import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Author} from "../../models/author";
import {UserService} from "../../services/user.service";
import {PostService} from "../../services/post.service";
import {PostList} from "../../models/postList";
import {TagService} from "../../services/tag.service";
import {Tag} from "../../models/tag";
import { StorageService } from 'src/app/services/storage.service';

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
  tagList:  Tag[][] = [];
  isDataLoaded: boolean = false;
  colors:Map<number,string> = new Map<number, string>();

  constructor(private userService: UserService,
              private postService: PostService,
              private tagService: TagService,
              private router: Router,
              public storageService: StorageService) {
  }

  ngOnInit() {
    this.userService.getFavoriteAuthors().subscribe((data: any) => {this.authors=data;});
    this.postService.getToPerMonth().subscribe((data: any) => {this.posts = data});
    this.tagService.getActual().subscribe((data: any) => {
      for (let i = 0; i < data.length/5; i++) {
        this.tagList[i] = []
        for (let y = 0; y < 5; y++) {
          this.tagList[i][y] = data[i+y]
          this.colors.set(data[i+y].id, this.getRandomColor())
        }
      }
      });
  }

  onErrorUserImage(event:any) {
    event.target.src = 'assets/iconsAccount.png';
  }

  onErrorPostImage(event:any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  setBackgroundColor(id:any){
    return this.colors.get(id)
  }

  getRandomColor() {
    return "hsl(" + Math.random() * 360 + ", 100%, 75%)";
  }

  goToArticles() {
    this.router.navigate(['posts']);
  }

  goToRegistration() {
    this.router.navigate(['register']);
  }

  goToAuthors() {
    this.router.navigate(['authors']);
  }
}
