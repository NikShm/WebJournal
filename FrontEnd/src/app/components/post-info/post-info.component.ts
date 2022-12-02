import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Tag} from "../../models/tag";
import {TagService} from "../../services/tag.service";
import {Author} from "../../models/author";
import {UserService} from "../../services/user.service";
import {StorageService} from "../../services/storage.service";

@Component({
  selector: 'app-post-info',
  templateUrl: './post-info.component.html',
  styleUrls: ['./post-info.component.css']
})
export class PostInfoComponent implements OnInit {

  @Input() post!: Post;
  @Input() authorId!: string;
  @Input() postId!: string;

  postImage: string = "assets/PostImage/post_";
  idButtonShowAction = "";
  classButtonShowAction = "";

  constructor(private postService: PostService,
              private tagService: TagService,
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private storageService: StorageService) { }

  ngOnInit(): void {
    if (this.storageService.getUser().id != this.authorId ||
      this.storageService.getUser().role != "AUTHOR"){
      this.idButtonShowAction = "notShow";
      this.classButtonShowAction = "notShow";
    } else{
      this.classButtonShowAction = ""
      this.idButtonShowAction = ""
    }
  }

  // @ts-ignore
  public showActions() {
    if (this.storageService.getUser().role != "AUTHOR") {
      return true;
    }
  }


  @Output() onEdit = new EventEmitter<boolean>();

  public enableEditMode() {
    this.onEdit.emit(true);
  }

  delete() {
    if (window.confirm("Are you sure you want to permanently delete this post?")) {
      this.postService.deletePost(this.postId).subscribe(() => {
        window.alert(`Post was deleted successfully.`);
        this.router.navigate(['/posts']);
      });
    }
  }
}
