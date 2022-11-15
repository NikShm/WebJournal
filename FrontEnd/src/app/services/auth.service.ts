import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalConstants } from '../global-constants';
import { LoginRequest } from '../models/login-request';

const AUTH_API = GlobalConstants.apiURL + '/auth/';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(login: LoginRequest): Observable<any> {
    return this.http.post(AUTH_API + 'login', login, httpOptions);
  }

  register(username: string, email: string, password: string, birthDate: string, bio: string): Observable<any> {
    return this.http.post(AUTH_API + 'register', {username, email, password, birthDate, bio}, httpOptions);
  }

  logout(): Observable<any> {
    return this.http.post(AUTH_API + 'logout', {}, httpOptions);
  }

  refreshToken() {
    return this.http.post(AUTH_API + 'refresh-token', {}, httpOptions);
  }
}
