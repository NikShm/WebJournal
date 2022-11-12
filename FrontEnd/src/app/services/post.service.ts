import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Brand } from "../models/brand";
import { Observable } from 'rxjs';
import {map} from "rxjs/operators";
import {PostList} from "../models/postList";
import {GlobalConstants} from "../global-constants";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getToPerMonth():Observable<PostList[]> {
    return this.http.get<PostList[]>(GlobalConstants.apiURL +'/api/posts/top-per-month?count=4').pipe(map((data: any) => {
      return data.map(function(post: any): PostList {
        return new PostList(post);
      })
    }));
  }
}
