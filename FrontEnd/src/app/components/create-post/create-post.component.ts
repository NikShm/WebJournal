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

  title(event: any): void {
    this.titleNumberOfCharacters = event.target.value.length;

    if (this.titleNumberOfCharacters > this.titleMaxNumberOfCharacters) {
      event.target.value = event.target.value.slice(0, this.titleMaxNumberOfCharacters);
      this.titleNumberOfCharacters = this.titleMaxNumberOfCharacters;
    }
  }

  foreword(event: any): void {
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

  constructor() { }

  ngOnInit(): void {
  }


}
