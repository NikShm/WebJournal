import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../services/post.service";
import {Post} from "../../models/post";
import {Subscription, switchMap} from "rxjs";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post!: Post;
  editedPost!: Post;
  editMode: boolean = false;
  id: number | undefined;
  private subscription: Subscription;

  constructor(private route: ActivatedRoute, private postService: PostService) {
    this.subscription = route.params.subscribe(params=>this.id=params['id']);
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => params.getAll('id'))
    )
      .subscribe(data=> this.id = +data);
    this.getPost();
  }

  private getPost(): void {
    this.postService.getOnePost(this.id!.toString()).subscribe(data => this.post = new Post(data));
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
