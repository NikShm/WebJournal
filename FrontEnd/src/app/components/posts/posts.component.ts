import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Tag} from 'src/app/models/tag';
import {PostService} from "../../services/post.service";
import {Sort} from "../../models/sort";

@Component({
    selector: 'app-products',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  page: Page = new Page([], 0);
  searchParameter!: Search;
  postImage: string = "assets/PostImage/post_";
  sort = "";


    apply() {
      this.searchParameter.page = 0
      this.search()

    }

  changePage(page: number) {
    this.searchParameter.page = page
    this.postService.setSearchParameter(this.searchParameter)
    this.search()
  }
  sorted(event:any){
    console.log(this.sort)
    console.log(event)
  }

    setSorted() {
        switch (this.sort) {
            case "Popular": {
                this.searchParameter.sortField = "likes";
                this.searchParameter.sortDirection = "ASC";
                break;
            }
            case "Unpopular": {
                this.searchParameter.sortField = "likes";
                this.searchParameter.sortDirection = "DESC";
                break;
            }
            case "New": {
                this.searchParameter.sortField = "published_at";
                this.searchParameter.sortDirection = "ASC";
                break;
            }
            case "Old": {
                this.searchParameter.sortField = "published_at";
                this.searchParameter.sortDirection = "DESC";
                break;
            }
          case "A to Z": {
            this.searchParameter.sortField = "title";
            this.searchParameter.sortDirection = "ASC";
            break;
          }
          case "Z to A": {
            this.searchParameter.sortField = "title";
            this.searchParameter.sortDirection = "DESC";
            break;
          }
            default: {
                break;
            }
        }
    }

    getFirst() {
        this.searchParameter.page = 0;
        // this.productService.setSearchPage(0);
        this.search()
    }

    getLast() {
      if (this.page.totalItem / this.searchParameter.page < 0){
        this.searchParameter.page = 0;
      } else {
        this.searchParameter.page = Math.floor(this.page.totalItem / this.searchParameter.pageSize);
      }
      console.log(this.searchParameter.page)
        this.search()
    }

    constructor(private postService: PostService,
        private router: Router) {
    }

    search() {
        this.postService.getPostPage().subscribe((page: Page) => {
          this.page = page
        });
    }

    ngOnInit() {
        this.search();
        this.searchParameter = this.postService.getSearchParameter();
    }

  onErrorPostImage(event:any) {
    event.target.src = 'assets/PostImage/default.png';
  }

}
