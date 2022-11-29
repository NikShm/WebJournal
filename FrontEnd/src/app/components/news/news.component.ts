import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PostService} from "../../services/post.service";
import {Search} from "../../models/search";
import {Page} from "../../models/pages";
import {PostList} from "../../models/postList";

@Component({
  selector: 'app-products',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  page: PostList[] = [];
  searchParameter = {page: 0,pageSize: 3}
  isDataLoaded: boolean = false;
  isDataFullLoaded: boolean = false;
  postImage: string = "assets/PostImage/post_";

  constructor(private router: Router, private postService:PostService) {
  }

  onErrorPostImage(event:any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  search() {
    this.postService.getNews(this.searchParameter).subscribe((page: PostList[]|null) => {
      console.log(page)
      if (page != null) {
        this.page = this.page.concat(page)
        this.searchParameter.page += 1;
        if(page?.length == 0){
          this.isDataFullLoaded = false
        }
      }
    });
  }

  ngOnInit() {
    this.postService.getNews(this.searchParameter).subscribe((page: PostList[]|null) => {
      if (page != null) {
        this.page = page
        this.isDataLoaded = true;
        this.isDataFullLoaded = true
      }
      this.searchParameter.page += 1;
    });
  }
}
