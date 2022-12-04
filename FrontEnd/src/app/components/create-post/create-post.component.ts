import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {FileHandle} from "../../app-routing.module";
import { AngularEditorConfig } from '@kolkov/angular-editor';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Tag} from "../../models/tag";
import {TagService} from "../../services/tag.service";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],

})
export class CreatePostComponent implements OnInit {
  @Input() post: Post = new Post();
  @Input() tag: Tag = new Tag();
  image!: File;
  @Input() mode: string = "CREATE";
  @Input() postId: string = "";
  previewImg = "assets/PostImage/post_";
  constructor(private postService: PostService,
              private tagService: TagService,
              private router: Router) { }

  ngOnInit(): void {
    if (JSON.parse(localStorage.getItem("auth-user")!).role !== "AUTHOR"){
      this.router.navigate(['/login']);
    }
  }

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
  toogleBool: boolean=true;

  changeEvent(event: any): void {
    this.toogleBool = !event.target.checked;
  }

  public previewImage() {
    if (this.mode === 'CREATE') {
      const fileReader = new FileReader();
      const file = (<HTMLInputElement>document.getElementById("uploadPhoto")).files![0];
      this.image = file;
      fileReader.readAsDataURL(file);
      fileReader.onload = function (fileReaderEvent) {
        (<HTMLImageElement>document.getElementById("previewImg")).src = fileReaderEvent.target?.result as string;
      }
    }
    if(this.mode === 'UPDATE'){
      const fileReader = new FileReader();
      const file = (<HTMLInputElement>document.getElementById("uploadPhoto")).files![0];
        this.image = file;
        fileReader.readAsDataURL(file);
        fileReader.onload = function (fileReaderEvent) {
          (<HTMLImageElement>document.getElementById("previewImg")).src = fileReaderEvent.target?.result as string;
        }
    }
  }

  onSave() {
    if (this.mode === 'CREATE') {
      this.postService.createPost(this.post, this.image).then((id: any) => {
        window.alert(`Post was created successfully with id ${id}.`);
        this.router.navigate([`/posts/${id}`]);
      })
    } else if (this.mode === 'UPDATE') {
      if(this.image == null){
        this.postService.updatePost(this.post).then(() => {
          window.alert(`Post was updated successfully with id №${this.postId}.`);
        })
      }
      else{
        this.postService.updateWithPhoto(this.post, this.image).then(() => {
          window.alert(`Post was updated successfully with id №${this.postId}.`);
        })
      }

      this.returnToInfo();
    }
  }

  exit() {
    if (this.mode === 'UPDATE') {
      this.returnToInfo();
    }
    else if (this.mode === 'CREATE') {
      this.router.navigate(['/posts']);
    }
  }

  @Output() onInfo = new EventEmitter<boolean>();

  public returnToInfo() {
    this.onInfo.emit(false);
  }


  /***********************************************/

  newTodo!: Tag | string;
  newTag = new Tag;

  addTodo(event: any) {
    if (this.newTodo instanceof Tag) {
      this.post.tags.push(this.newTodo);
    }
    else{
      this.newTag.name = this.newTodo;
      this.post.tags.push(this.newTag);
      this.newTag = new Tag;
    }
    this.newTodo = '';
    event.preventDefault();
  }

  deleteTodo(index: any) {
    window.confirm("Are you sure you want to delete this tag?")
    this.post.tags.splice(index, 1);
  }

  keyword = 'name';
  tags:any[] = [];

  getTags(name:string){
    this.postService.getListTagForCreatePage().subscribe((tags:any)=>{this.tags = tags;
      console.log(this.tags)})
  }

}
