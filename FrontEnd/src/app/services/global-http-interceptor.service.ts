import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, switchMap, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalHttpInterceptorService implements HttpInterceptor {
    private isRefreshing = false;

    constructor(
        private router: Router, 
        private storageService: StorageService,
        private authService: AuthService
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
            withCredentials: true
        });
        
        return next.handle(req).pipe(
            catchError((error) => {
                let errorMessage = "other error";
                if (error instanceof HttpErrorResponse) {
                    if (error.error instanceof ErrorEvent) {
                        errorMessage = `client side error: ${error.error.message}`;
                    }
                    else {
                        errorMessage = `server error status: ${error.error.status} ${error.statusText}\n${error.error.message}`;
                        switch (error.status) {
                            case 401:
                                if (!req.url.includes('auth/login')) {
                                    return this.handle401Error(req, next);
                                }
                                break;
                            case 403:
                                this.router.navigate(['/403']);
                                break;
                            case 404:
                                if (req.method === 'GET') {
                                    this.router.navigate(['/404']);
                                }
                                else {
                                    window.alert(errorMessage);
                                }
                                break;
                            case 500:
                                errorMessage += "\nPlease try again";
                                window.alert(errorMessage);
                                break;
                        }
                    }
                }
                console.error(errorMessage);
                return throwError(() => error);
            })
        );
    }

    private handle401Error(req: HttpRequest<any>,  next: HttpHandler) {
        if (!this.isRefreshing) {
            this.isRefreshing = true;

            if (this.storageService.isLoggedIn()) {
                return this.authService.refreshToken().pipe(
                    switchMap(() => {
                        this.isRefreshing = false;
                        return next.handle(req);
                    }),
                    catchError((error) => {
                        this.isRefreshing = false;
                        if (error.status === 403) {
                            this.authService.logout().subscribe({
                                next: () => {
                                    this.storageService.clear();
                                    window.location.reload();
                                },
                                error: err => {
                                    console.error(err);
                                }
                            });
                        }
                        return throwError(() => error);
                    })
                );
            }
        }
        
        return next.handle(req);
    }
}