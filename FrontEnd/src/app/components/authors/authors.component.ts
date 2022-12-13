import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {Page} from "../../models/pages";
import {User} from "../../models/user";
@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {
  searchParameter = {sortField: "id", sortDirection: "ASC", page:0, pageSize: 12, searchPattern:{search:""}}
  page:Page = new Page([], 0);
  sort = "";
  userImage: string = "assets/UsersIcon/user_";

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

  onErrorUserImage(event:any) {
    event.target.src = 'assets/UsersIcon/default.png';
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
