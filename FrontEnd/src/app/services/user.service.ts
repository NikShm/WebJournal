import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Author} from "../models/author";
import {GlobalConstants} from "../global-constants";

import {Page} from "../models/pages";
import {Search} from "../models/search";


@Injectable({
    providedIn: 'root'
})
export class UserService {
  searchParameter = new Search("title", "ASC", 0,2,{search:""})

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
      return this.http.post(GlobalConstants.apiURL +'/users/search', search).pipe(map((data: any) => {
        data.content = data.content.map((author:Author) => {
          return new Author(author);
        })
        return new Page(data.content, data.totalItem)
      }));
    }

    getSearchParameter(){
      return this.searchParameter
    }

    setSearchParameter(searchParameter:Search){
      console.log(this.searchParameter)
    }

    getPublicUser(id: string): Observable<User> {
      return this.http.get<User>(GlobalConstants.apiURL + '/users/public/' + id);
    }

    getFullUser(id: string): Observable<User> {
      return this.http.get<User>(GlobalConstants.apiURL + '/users/full/' + id);
    }

    async editUser(user: User, image: File | null) {
      if(image) {
        const userData = new FormData();
        userData.append('photo', image);
        userData.append('id', user.id);
        await this.http.post(GlobalConstants.apiURL + '/users/upload-photo/', userData).toPromise();
      }
      await this.http.put(GlobalConstants.apiURL + '/users/', {id: user.id, username: user.username, bio: user.bio}).toPromise();
    }

    deleteUserPhoto(id: string) {
      return this.http.get(GlobalConstants.apiURL + '/users/delete-photo/' + id);
    }

    changeUserRole(id: string, role: string) {
      return this.http.put(GlobalConstants.apiURL + '/users/role', {id: id, role: role});
    }

    deleteUserAccount(id: string) {
      return this.http.delete(GlobalConstants.apiURL + '/users/' + id);
    }

    followUser(userId: string, userToFollowId: string) {
      return this.http.get(GlobalConstants.apiURL + `/users/${userId}/follow/${userToFollowId}`);
    }

    unfollowUser(userId: string, userToUnfollowId: string) {
      return this.http.delete(GlobalConstants.apiURL + `/users/${userId}/unfollow/${userToUnfollowId}`);
    }
}
