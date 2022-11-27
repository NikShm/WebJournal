import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../services/post.service";
import {Post} from "../../models/post";


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
    this.getProduct();
  }

  private getProduct(): void {
    const id: string = this.route.snapshot.paramMap.get('id') ?? '';
    this.postService.getOnePost(id).subscribe(data => this.post = new Post(data));
  }

  changeEditMode(value: boolean) {
    this.getProduct();
    this.editMode = value;
  }

  showEditForm(value: boolean) {
    this.editedPost = new Post(this.post);
    this.editMode = value;
  }
}
