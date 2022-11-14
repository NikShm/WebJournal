import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tag } from "../models/tag";
import { Observable } from 'rxjs';
import {map} from "rxjs/operators";
import {PostList} from "../models/postList";
import {GlobalConstants} from "../global-constants";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(private http: HttpClient) { }

  getActual():Observable<Tag[]> {
    return this.http.get<Tag[]>(GlobalConstants.apiURL +'/api/tags/actual?count=20').pipe(map((data: any) => {
      return data.map(function(tag: any): Tag {
        return new Tag(tag);
      })
    }));
  }
}
