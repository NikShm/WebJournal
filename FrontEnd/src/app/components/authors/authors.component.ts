import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {Page} from "../../models/pages";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {
  searchParameter = {sortField: "id", sortDirection: "ASC", page:0, pageSize: 2, searchPattern:{}}
  page:Page = new Page([], 0);

  constructor(private authorService: UserService ) { }

  ngOnInit(): void {
  this.authorService.getAuthors(this.searchParameter).subscribe((data)=>{
    this.page = data
    console.log(data)})

  }
  getFirst() {
    this.searchParameter.page = 0;
    this.search()
  }
  search() {

  }

  getLast() {
    if (this.page.totalItem / this.searchParameter.page < 0){
      this.searchParameter.page = 0;
    } else {
      this.searchParameter.page = Math.floor(this.page.totalItem / this.searchParameter.pageSize);
    }
    this.search()
  }

}
