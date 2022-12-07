import { Component, EventEmitter, OnInit, Output } from '@angular/core';
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
  editedUser!: User;
  private id: string = this.route.snapshot.paramMap.get('id') ?? '';
  editMode: boolean = false;
  @Output() onDetails = new EventEmitter<boolean>();

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.getUser();
  }

  private getUser() {
    this.userService.getUser(this.id).subscribe(data => this.user = new User(data));
  }

  changeEditMode(value: boolean) {
    this.getUser();
    this.editMode = value;
  }

  showEditForm(value: boolean) {
    this.editedUser = new User(this.user);
    this.editMode = value;
  }
}
