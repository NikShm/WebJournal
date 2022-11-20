import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {
  searchParameter = {sortField: "id", sortDirection: "ASC", page:0, pageSize: 2, searchPattern:{}}

  constructor(private authorService: UserService ) { }

  ngOnInit(): void {
  this.authorService.getAuthors(this.searchParameter).subscribe((data)=>{
    console.log(data)})
  }

}
