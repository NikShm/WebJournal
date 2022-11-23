import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {map} from "rxjs/operators";
import {PostList} from "../models/postList";
import {GlobalConstants} from "../global-constants";
import {Post} from "../models/post";
import {StorageService} from "./storage.service";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient,
              private storageService: StorageService) { }

  getToPerMonth():Observable<PostList[]> {
    return this.http.get<PostList[]>(GlobalConstants.apiURL +'/posts/top-per-month?count=4').pipe(map((data: any) => {
      return data.map(function(post: any): PostList {
        return new PostList(post);
      })
    }));
  }

  async createPost(postToCreate: Post) {
    let postData: Post | FormData;
    postData = new Post(postToCreate);
    const id = <string>await this.http.post(GlobalConstants.apiURL + '/posts/create', postData).toPromise();
    window.alert(`Post was created successfully with id ${id}.`);
    postData = new FormData();
    postData.append('id', id);

    return id;
  }
}
