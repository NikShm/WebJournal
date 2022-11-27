import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-info',
  templateUrl: './post-info.component.html',
  styleUrls: ['./post-info.component.css']
})
export class PostInfoComponent implements OnInit {

  @Input() post!: Post;
  postImage: string = "assets/PostImage/post_";
  idButtonShowAction = "";
  classButtonShowAction = "";

  constructor(private postService: PostService,
              private router: Router) { }

  ngOnInit(): void {
  }

  public showActions() {
    if (JSON.parse(localStorage.getItem("user")!).role == "ADMIN") {
      document.getElementById("dropdown-content-actions")?.classList.toggle("show");
      document.getElementById("actions")?.classList.toggle("btn-dropdown-active");
    }
  }

  @HostListener('window:click', ['$event'])
  public onClick(event: MouseEvent) {
    if (!(<HTMLElement>event.target).matches('#actions')) {
      const dropdown = document.getElementById("dropdown-content-actions");
      const button = document.getElementById("actions");
      if (dropdown?.classList.contains('show')) {
        dropdown.classList.remove('show');
      }
      if (button?.classList.contains('btn-dropdown-active')) {
        button.classList.remove('btn-dropdown-active');
      }
    }
  }

  @Output() onEdit = new EventEmitter<boolean>();

  public enableEditMode() {
    this.onEdit.emit(true);
  }

  delete() {
    if (window.confirm("Are you sure you want to permanently delete this post?")) {
      this.postService.deletePost(this.post.id).subscribe(() => {
        window.alert(`Post was deleted successfully.`);
        this.router.navigate(['/posts']);
      });
    }
  }
}
