import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Author} from "../models/author";
import {GlobalConstants} from "../global-constants";

import {Page} from "../models/pages";
import {Authors} from "../models/authors";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient) {
    }

    getFavoriteAuthors(): Observable<Author> {
        return this.http.get<Author>(GlobalConstants.apiURL +'/users/top?count=6').pipe(map((data: any) => {
          data = data.map((author:Author) => {
            return new Author(author);
          })
            return data;
        }));
    }
    getAuthors(search: any): Observable<Page> {
      return this.http.post(GlobalConstants.apiURL +'/api/users/search', search).pipe(map((data: any) => {
        data.content = data.content.map((author:Authors) => {
          return new Authors(author);
        })
        return new Page(data.content, data.totalItem)
      }));
    }

    getUser(username: string): Observable<User> {
      return this.http.get<User>(GlobalConstants.apiURL + '/users/' + username);
    }
}
