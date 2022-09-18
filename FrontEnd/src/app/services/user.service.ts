import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Product} from "../models/product";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private user:User = {login:"user", role:"CUSTOMER"}
    constructor(private http: HttpClient) {
    }

    getUser(login: string, password:string): Observable<User> {
        return this.http.get<User>('http://localhost:8080/api/user/login=' + login + "/password=" + password).pipe(map((data: any) => {
            if(data != null) {
                return new User(data);
            }
            return this.user;
        }));
    }
}