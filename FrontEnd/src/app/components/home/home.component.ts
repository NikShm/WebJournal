import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Author} from "../../models/author";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  userImage: string = "assets/UsersIcon/user_";
  authors: Author[] = [];

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
    this.userService.getFavoriteAuthors().subscribe((data: any) => {this.authors=data;});
  }

  onError(event:any) {
    event.target.src = 'assets/iconsAccount.png';
  }
}
