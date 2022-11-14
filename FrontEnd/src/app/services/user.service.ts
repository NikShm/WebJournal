import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Product} from "../models/product";
import {Author} from "../models/author";
import {GlobalConstants} from "../global-constants";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private user:User = {login:"user", role:"CUSTOMER"}
    constructor(private http: HttpClient) {
    }

    getFavoriteAuthors(): Observable<User> {
        return this.http.get<User>(GlobalConstants.apiURL +'/api/users/top?count=6').pipe(map((data: any) => {
          data = data.map((author:Author) => {
            return new Author(author);
          })
            return data;
        }));
    }
}
