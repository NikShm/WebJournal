import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['../profile.component.css', './profile-edit.component.css']
})
export class ProfileEditComponent implements OnInit {
  usernameLength: string = "0";
  bioLength: string = "0";
  emailLength: string = "0";

  constructor() { }

  ngOnInit(): void {
  }
  
  ngAfterViewInit() {
    document.getElementById('footer')?.classList.add('footer-margin');
  }

  onInput(target: any) {
    if (target.name === "username") {
      this.usernameLength = target.value.length;
    }
    else if (target.name === "bio") {
      this.bioLength = target.value.length;
    }
    else if (target.name === "email") {
      this.emailLength = target.value.length;
    }
  }
}
