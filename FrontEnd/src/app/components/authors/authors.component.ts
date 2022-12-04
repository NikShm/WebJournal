import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {Page} from "../../models/pages";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {
  searchParameter = {sortField: "id", sortDirection: "ASC", page:0, pageSize: 12, searchPattern:{search:""}}
  page:Page = new Page([], 0);
  sort = "";

  constructor(private authorService: UserService ) { }

  ngOnInit(): void {
  this.authorService.getAuthors(this.searchParameter).subscribe((data)=>{
    this.page = data
    console.log(data)})

  }
  changePage(page: number) {
    this.searchParameter.page = page
    this.authorService.setSearchParameter(this.searchParameter)
    this.search()
  }
  sorted(event:any){
    console.log(this.sort)
    console.log(event)
  }

  setSorted(event: any) {
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

  apply() {
    this.searchParameter.page = 0
    this.search()

  }
  search() {
    this.authorService.getAuthors(this.searchParameter).subscribe((page: Page) => {
      this.page = page
    });
  }

}
