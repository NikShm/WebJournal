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



  constructor(private http: HttpClient) { }

  getComments(searchParameter:Search):Observable<Page>{
    // this.searchParameter.page+=1
    console.log(searchParameter)
    return this.http.post(GlobalConstants.apiURL +'/comments/search', searchParameter).pipe(map((data: any) => {
      data.content = data.content.map((comment:Comment) => {
        return new Comment(comment)
      })
      return new Page(data.content, data.totalItem)
    }));
  }


  createComment(comment: Comment) {
    this.http.post(GlobalConstants.apiURL + '/comments/create/', comment).subscribe();
  }

  deleteComment(id: string) {
    this.http.delete(GlobalConstants.apiURL + '/comments/' + id).subscribe();
  }
}
