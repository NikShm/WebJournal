import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalConstants } from '../global-constants';
import { LoginRequest } from '../models/login-request';
import { RegistrationRequest } from '../models/registration-request';

const AUTH_API = GlobalConstants.apiURL + '/auth/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(login: LoginRequest): Observable<any> {
    return this.http.post(AUTH_API + 'login', login);
  }

  register(reg: RegistrationRequest): Observable<any> {
    return this.http.post(AUTH_API + 'register', reg);
  }

  logout(): Observable<any> {
    return this.http.post(AUTH_API + 'logout', {});
  }

  refreshToken(): Observable<any> {
    return this.http.post(AUTH_API + 'refresh-token', {});
  }
}
