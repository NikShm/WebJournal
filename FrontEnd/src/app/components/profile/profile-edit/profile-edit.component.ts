import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['../profile.component.css', './profile-edit.component.css']
})
export class ProfileEditComponent implements OnInit {
  @Input() user!: User;
  @Output() onDetails = new EventEmitter<boolean>();
  usernameLength!: string;
  bioLength!: string;
  image!: File | null;
  editFailed: boolean = false;
  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private storageService: StorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.usernameLength = String(this.user.username.length);
    this.bioLength = String(this.user.bio.length);
  }

  onInput(target: any) {
    if (target.name === "username") {
      this.usernameLength = target.value.length;
    }
    else if (target.name === "bio") {
      this.bioLength = target.value.length;
    }
  }

  exit() {
    this.onDetails.emit(false);
  }

  onEdit() {
    if ((<HTMLImageElement>document.getElementById("preview-img")).src.includes("default.png")) {
      this.userService.deleteUserPhoto(this.user.id).subscribe();
    }
    this.userService.editUser(this.user, this.image).then(
      () => this.exit(),
      err => {
        this.errorMessage = err.error.message;
        this.editFailed = true;
      }
    );
  }

  previewImage() {
    const fileReader = new FileReader();
    const file = (<HTMLInputElement>document.getElementById("profile-picture")).files![0];
    this.image = file;
    fileReader.readAsDataURL(file);
    fileReader.onload = function(fileReaderEvent) {
      (<HTMLImageElement>document.getElementById("preview-img")).src = fileReaderEvent.target?.result as string;
    }
  }

  onErrorUserImage(event:any) {
    event.target.src = 'assets/UsersIcon/default.png';
  }

  deletePhoto() {
    (<HTMLImageElement>document.getElementById("preview-img")).src = "assets/UsersIcon/default.png";
  }
}
