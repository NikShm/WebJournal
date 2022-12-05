import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user!: User;
  private username: string = this.route.snapshot.paramMap.get('username') ?? '';
  editMode: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.getUser(this.username).subscribe(data => this.user = new User(data));
  }

  showEditForm(value: boolean) {
    this.editMode = value;
  }
}
