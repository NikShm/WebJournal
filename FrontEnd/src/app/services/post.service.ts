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

  async createPost(postToCreate: Post, image: File) {
    let postData: Post | FormData;
    postData = new Post(postToCreate);
    const id = <string>await this.http.post(GlobalConstants.apiURL + '/posts/create', postData).toPromise();
    const newPath = `post_${id}.jpg`;
    postData = new FormData();
    postData.append('id', id);

    let imageData: FormData;
    imageData = new FormData();
    imageData.append('photo', image);
    imageData.append('newPath', newPath);
    await this.http.post(GlobalConstants.apiURL + '/posts/uploadPhoto/', imageData).toPromise();

    return id;
  }

  getSearchParameter(){
    return this.searchParameter
  }

  setSearchParameter(searchParameter:Search){
    console.log(this.searchParameter)
  }

  getPostPage():Observable<Page> {
    return this.http.post(GlobalConstants.apiURL +'/posts/search', this.searchParameter).pipe(map((data: any) => {
      data.content = data.content.map((post:PostList) => {
        return new PostList(post)
      })
      return new Page(data.content, data.totalItem)
    }));
  }

  getListTag():any {
    return this.http.get(GlobalConstants.apiURL +'/tags/tag='+this.searchParameter.searchPattern.searchTag).pipe(map((data: any) => {
      data = data.map((tag:Tag) => {
        return new Tag(tag)
      })
      return data
    }));
  }
}
