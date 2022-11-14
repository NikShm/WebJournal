import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {
  searchParameter = {sortField: "id", sortDirection: "ASC", page:0, pageSize: 2}

  constructor(private authorService: UserService ) { }

  ngOnInit(): void {
  console.log(this.authorService.getAuthors(this.searchParameter))
  }

}
