/*import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
*/

import { Component, OnInit,  Input } from '@angular/core';
import {FileHandle} from "../../app-routing.module";
import { AngularEditorConfig } from '@kolkov/angular-editor';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../services/storage.service";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],

})
export class CreatePostComponent implements OnInit {
  htmlContent = '';

  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    toolbarHiddenButtons: [
      ['bold']
    ],
    customClasses: [
      {
        name: "quote",
        class: "quote",
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: "titleText",
        class: "titleText",
        tag: "h1",
      },
    ]
  };
  @Input()
  uploadedFiles: FileHandle[] | undefined;
  titleMaxNumberOfCharacters = 80;
  forewordMaxNumberOfCharacters = 150;

  titleNumberOfCharacters = 0;
  forewordNumberOfCharacters = 0;

  counter = true;

  interaction = {
    textValue: '',
    inputValue: ''
  };

  titleInput(event: any): void {
    this.titleNumberOfCharacters = event.target.value.length;

    if (this.titleNumberOfCharacters > this.titleMaxNumberOfCharacters) {
      event.target.value = event.target.value.slice(0, this.titleMaxNumberOfCharacters);
      this.titleNumberOfCharacters = this.titleMaxNumberOfCharacters;
    }
  }

  forewordInput(event: any): void {
    this.forewordNumberOfCharacters = event.target.value.length;

    if (this.forewordNumberOfCharacters > this.forewordMaxNumberOfCharacters) {
      event.target.value = event.target.value.slice(0, this.forewordMaxNumberOfCharacters);
      this.forewordNumberOfCharacters = this.forewordMaxNumberOfCharacters;
    }
  }

  /*****************************************/
  filesDropped(files: FileHandle[]) {
    this.uploadedFiles = files;
  }

  @Input() post: Post = new Post();
  @Input() mode: string = "create";

  constructor(private postService: PostService,
              private router: Router,
              private storageService: StorageService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    if (JSON.parse(localStorage.getItem("auth-user")!).role !== "AUTHOR"){
      this.router.navigate(['/login']);
    }
  }
  onSave() {
    if (this.mode === 'create') {
      this.post.author_id = this.storageService.getUser().id;
      this.postService.createPost(this.post).then((id: any) => {
        window.alert(`Post was created successfully with id ${id}.`);
      })
    }
  }
}
