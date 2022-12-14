import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tag } from "../models/tag";
import { Observable } from 'rxjs';
import {map} from "rxjs/operators";
import {GlobalConstants} from "../global-constants";
import {Post} from "../models/post";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(private http: HttpClient) { }

  getActual():Observable<Tag[]> {
    return this.http.get<Tag[]>(GlobalConstants.apiURL +'/tags/actual?count=20').pipe(map((data: any) => {
      return data.map(function(tag: any): Tag {
        return new Tag(tag);
      })
    }));
  }

  async createTag(tag: Tag) {
    let tagData: Tag | FormData;
    tagData = new Tag(tag);
    const id = <string>await this.http.post(GlobalConstants.apiURL + '/tags/create/', tagData).toPromise();
    tagData = new FormData();
    tagData.append('id', id);

    return id;
  }
}
