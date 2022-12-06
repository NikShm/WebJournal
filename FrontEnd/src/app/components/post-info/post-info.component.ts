import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TagService} from "../../services/tag.service";
import {UserService} from "../../services/user.service";
import {StorageService} from "../../services/storage.service";
import {PostList} from "../../models/postList";

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
  similarPosts: PostList[] = [];
  approvedButton!:boolean;
  canselApprovedButton!:boolean;
  classButton = "heart"

  constructor(private postService: PostService,
              private tagService: TagService,
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private storageService: StorageService) {
  }

  ngOnInit(): void {
    console.log("Yes")
    if (this.storageService.getUser().id != this.authorId) {
      if (this.showActions()) {
        this.classButtonShowAction = ""
        this.idButtonShowAction = ""
      } else {
        this.idButtonShowAction = "notShow";
        this.classButtonShowAction = "notShow";
      }
    } else {
      this.classButtonShowAction = ""
      this.idButtonShowAction = ""
    }

    this.postService.getSimilar(this.postId).subscribe((posts) => {
      this.similarPosts = posts
    })

    this.approvedButton = this.showApprovedButton()
    this.canselApprovedButton = this.showCancelApprovedButton()
  }

  // @ts-ignore
  showActions() {
    if (this.storageService.getUser().role != "AUTHOR") {
      return true;
    }
  }

// @ts-ignore
  showApprovedButton() {
    return this.storageService.getUser().id != this.authorId && !this.post.approved;
  }

  // @ts-ignore
  showCancelApprovedButton() {
    return this.storageService.getUser().id != this.authorId && this.post.approved;
  }

  // @ts-ignore
  showAuthorsButton() {
    return this.storageService.getUser().id == this.authorId
  }

  approved() {
   this.postService.approved(this.postId).subscribe((data)=>{
     if (data){
       this.approvedButton = false
       this.canselApprovedButton = true
     }
   })
  }

  canselApproved() {
    this.postService.cancelApproved(this.postId).subscribe((data)=>{
      console.log(data)
      if (data){
        this.approvedButton = true
        this.canselApprovedButton = false
      }
    })
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

  setTag(event: any) {
    this.postService.setTagSearch(event.target.value)
    this.router.navigate(['/posts'])
  }

  onErrorPostImage(event: any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  goToPost(id:any){
    this.router.navigateByUrl('/posts', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/posts/' + id]);
    });
  }

  on() {
    this.classButton = "heart heart-active"
    this.ngOnInit()
  }
}
