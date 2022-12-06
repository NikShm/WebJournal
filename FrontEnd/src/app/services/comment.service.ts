import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PostList} from "../models/postList";
import {GlobalConstants} from "../global-constants";
import {map} from "rxjs/operators";
import {Page} from "../models/pages";
import {Comment} from "../models/comment";
import {Search} from "../models/search";
import {Post} from "../models/post";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  searchParameter = new Search("createdAt", "DESC", 0,2, {postId:""})

  constructor(private http: HttpClient) { }

  getComments():Observable<Page>{
    return this.http.post(GlobalConstants.apiURL +'/comments/search', this.searchParameter).pipe(map((data: any) => {
      data.content = data.content.map((comment:Comment) => {
        return new Comment(comment)
      })
      return new Page(data.content, data.totalItem)
    }));
  }

  setPage(){
    this.searchParameter.page++
  }

  setPostId(id:any){
    this.searchParameter.searchPattern.postId = id.valueOf()
  }

  createComment(comment: Comment) {
    this.http.post(GlobalConstants.apiURL + '/comments/create/', comment).subscribe();
  }

  deleteComment(id: string) {
    this.http.delete(GlobalConstants.apiURL + '/comments/' + id).subscribe();
  }
}
