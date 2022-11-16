import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Tag} from 'src/app/models/tag';
import {PostService} from "../../services/post.service";
import {Sort} from "../../models/sort";
import {User} from "../../models/user";
import {Observable} from "rxjs";
import {AutocompleteLibModule} from 'angular-ng-autocomplete';
import {Page} from "../../models/pages";
import {Search} from "../../models/search";

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
  keyword = 'name';
  tags:any[] = [];

    apply() {
      this.searchParameter.page = 0
      this.search()

    }

    getTags(name:string){
      this.searchParameter.searchPattern.searchTag = name
      this.postService.getListTag().subscribe((tags:any)=>{this.tags = tags;
        console.log(this.tags)})
    }

  selectedTags(name:Tag){
    this.searchParameter.searchPattern.searchTag = name.name
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

    setSorted(event:any) {
      console.log(event.active)
      this.searchParameter.sortField = event.active
      if (this.searchParameter.sortDirection === "ASC"){
        this.searchParameter.sortDirection ="DESC";
      } else {
        this.searchParameter.sortDirection = "ASC"
      }
      this.search()
    }

    getFirst() {
        this.searchParameter.page = 0;
        this.search()
    }

    getLast() {
      if (this.page.totalItem / this.searchParameter.page < 0){
        this.searchParameter.page = 0;
      } else {
        this.searchParameter.page = Math.floor(this.page.totalItem / this.searchParameter.pageSize);
      }
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
        this.postService.getListTag().subscribe((tags:any)=>{this.tags = tags
          console.log(this.tags)})
    }

  onErrorPostImage(event:any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  onFocused($event: void) {

  }
}
