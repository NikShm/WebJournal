import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../services/post.service";
import {Post} from "../../models/post";
import {map} from "rxjs/operators";
import {Tag} from "../../models/tag";
import {Observable} from "rxjs";
import {PostList} from "../../models/postList";
import {GlobalConstants} from "../../global-constants";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post!: Post;
  editedPost!: Post;
  editMode: boolean = false;

  constructor(private route: ActivatedRoute, private postService: PostService) { }

  ngOnInit(): void {
    this.getPost();
  }

  private getPost(): void {
    const id: string = this.route.snapshot.paramMap.get('id') ?? '';
    this.postService.getOnePost(id).subscribe(data => this.post = new Post(data));
  }

  changeEditMode(value: boolean) {
    this.getPost();
    this.editMode = value;
  }

  showEditForm(value: boolean) {
    this.editedPost = new Post(this.post);
    this.editMode = value;
  }
}
