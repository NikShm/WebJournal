import { HttpClient } from '@angular/common/http';
import {Injectable, OnInit} from '@angular/core';
import { Tag } from "../models/tag";
import { Observable } from 'rxjs';
import {map} from "rxjs/operators";
import {PostList} from "../models/postList";
import {GlobalConstants} from "../global-constants";
import {Post} from "../models/post";
import {StorageService} from "./storage.service";
import {Page} from "../models/pages";
import {Search} from "../models/search";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  searchParameter = new Search("title", "ASC", 0,3,{search:"", searchTag:""})

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

  getSearchParameter(){
    return this.searchParameter
  }

  setSearchParameter(searchParameter:Search){
    console.log(this.searchParameter)
  }

  getPostPage():Observable<Page> {
    return this.http.post(GlobalConstants.apiURL +'/api/posts/search', this.searchParameter).pipe(map((data: any) => {
      data.content = data.content.map((post:PostList) => {
        return new PostList(post)
      })
      console.log(data)
      return new Page(data.content, data.totalItem)
    }));
  }
}
