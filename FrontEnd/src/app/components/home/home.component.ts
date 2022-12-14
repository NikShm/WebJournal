import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Author} from "../../models/author";
import {UserService} from "../../services/user.service";
import {PostService} from "../../services/post.service";
import {PostList} from "../../models/postList";
import {TagService} from "../../services/tag.service";
import {Tag} from "../../models/tag";
import {StorageService} from 'src/app/services/storage.service';

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
      let tagListNumber= 0
      for (let i = 0; i < data.length/5; i++) {
        this.tagList[i] = []
        for (let y = 0; y < 5 && tagListNumber<data.length; y++) {
          this.tagList[i][y] = data[tagListNumber]
          this.colors.set(data[tagListNumber].id, this.getRandomColor())
          tagListNumber++;
        }
      }
      this.isDataLoaded = true
      });
  }

  onErrorUserImage(event:any) {
    event.target.src = 'assets/UsersIcon/default.png';
  }

  onErrorPostImage(event:any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  setBackgroundColor(id:any){
    return this.colors.get(id)
  }

  setTag(name: string) {
    console.log(name)
    this.postService.setTagSearch(name)
    this.router.navigate(['/posts'])
  }

  getRandomColor() {
    let sounds = ["hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  44  +", 79%, 53%)", //yellow
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  0  +", 0%, 0%, 0%)",
                  "hsl(" +  348  +", 85%, 73%)", //pink
                  "hsl(" +  24  +", 83%, 56%)",//orange
                  "hsl(" +  200  +", 84%, 67%)", //blue
                  "hsl(" +  166  +", 18%, 54%)"] //green
    return sounds[Math.floor(Math.random() * sounds.length)];
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
