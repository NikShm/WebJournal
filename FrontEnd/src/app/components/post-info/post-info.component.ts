import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TagService} from "../../services/tag.service";
import {UserService} from "../../services/user.service";
import {StorageService} from "../../services/storage.service";
import {PostList} from "../../models/postList";
import {CommentService} from "../../services/comment.service";
import {Page} from "../../models/pages";
import {Comment} from "../../models/comment";

@Component({
  selector: 'app-post-info',
  templateUrl: './post-info.component.html',
  styleUrls: ['./post-info.component.css']
})
export class PostInfoComponent implements OnInit {

  @Input() post!: Post;
  @Input() authorId!: string;
  @Input() postId!: string;

  userImage: string = "assets/UsersIcon/user_";
  postImage: string = "assets/PostImage/post_";
  idButtonShowAction = "";
  classButtonShowAction = "";
  similarPosts: PostList[] = [];
  approvedButton!: boolean;
  canselApprovedButton!: boolean;
  likeButton = "heart"
  comments: Page = new Page([], 0);
  searchParameter = {page: 0,pageSize: 9}
  isDataFullLoaded: boolean = true;
  isCreateComment: boolean = false;
  commentText: string = "";


  constructor(private postService: PostService,
              private tagService: TagService,
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private storageService: StorageService,
              private commentService:CommentService) {
  }

  ngOnInit(): void {
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

    if (this.post.like == null){
      this.likeButton = ""
    }

    if (this.storageService.isLoggedIn()){
      this.isCreateComment = true
    }

    this.postService.getSimilar(this.postId).subscribe((posts) => {
      this.similarPosts = posts
    })

    this.approvedButton = this.showApprovedButton()
    this.canselApprovedButton = this.showCancelApprovedButton()

    switch (this.post.like) {
      case null:
        this.likeButton = ""
        break;
      case true:
        this.likeButton = "heart heart-active"
        break;
      case false:
        this.likeButton = "heart"
        break;
    }
    this.commentService.setPostId(this.postId)
    this.search()
  }

  search() {
    this.commentService.getComments().subscribe((page: Page) => {
      if (page.content.length != 0) {
        this.comments.content = this.comments.content.concat(page.content)
        this.commentService.setPage();
      }
        else {
          this.isDataFullLoaded = false
        }
      console.log(this.comments)
      }
    );
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

  showCommentButton(id:any) {
    return this.storageService.getUser().id == id;
  }

  approved() {
    this.postService.approved(this.postId).subscribe((data) => {
      if (data) {
        this.approvedButton = false
        this.canselApprovedButton = true
      }
    })
  }

  canselApproved() {
    this.postService.cancelApproved(this.postId).subscribe((data) => {
      console.log(data)
      if (data) {
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

  createComment(){
    let comment = new Comment()
    console.log(this.commentText)
    comment.text = this.commentText
    comment.postId = this.post.id
    comment.author = this.post.author
    this.comments.content.unshift(comment)
    this.commentService.createComment(comment)
  }

  deleteComment(comment:any){
    if (window.confirm("Are you sure you want to permanently delete this post?")) {
      this.commentService.deleteComment(comment.id)
      this.comments.content = this.comments.content.filter(obj => obj !== comment);
    }
  }

  setTag(event: any) {
    this.postService.setTagSearch(event.target.value)
    this.router.navigate(['/posts'])
  }

  onErrorPostImage(event: any) {
    event.target.src = 'assets/PostImage/default.png';
  }

  onErrorUserImage(event:any) {
    event.target.src = 'assets/UsersIcon/default.png';
  }

  goToPost(id: any) {
    this.router.navigateByUrl('/posts', {skipLocationChange: true}).then(() => {
      this.router.navigate(['/posts/' + id]);
    });
  }

  like() {
    if (this.storageService.getUser().id != this.authorId) {
      if (this.likeButton == "heart") {
        this.likeButton = "heart heart-active"
        this.postService.like(this.postId).subscribe();
        this.post.likes++
      }
      else {
        this.likeButton = "heart"
        this.postService.dislike(this.postId).subscribe()
        this.post.likes--
      }
    }
  }
}
