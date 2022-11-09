import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import {FormsModule} from "@angular/forms";
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {NgxPaginationModule} from 'ngx-pagination';


import { MatCardModule } from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { AppComponent } from './app.component';
import { PostsComponent } from './components/posts/posts.component';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/shared/layout/header/header.component';
import { FooterComponent } from './components/shared/layout/footer/footer.component';
import { PostComponent } from './components/post/post.component';
import { ArticlesComponent } from './components/articles/articles.component';
import { AuthorsComponent } from './components/authors/authors.component';
import { RegistrationComponent } from './registration/registration.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { RulesComponent } from './components/rules/rules.component';
import { LoginComponent } from './login/login.component';
import { DialogConfirmComponent } from './components/dialog-confirm/dialog-confirm.component';
import { ImageDragDirective } from './image-drag.directive';

import {GlobalHttpInterceptorService} from "./services/global-http-interceptor.service";
import { AuthService } from './services/auth.service';

import { AngularEditorModule } from '@kolkov/angular-editor';
import { UserpageComponent } from './userpage/userpage.component';
@NgModule({
  declarations: [
    AppComponent,
    PostsComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    PostComponent,
    ArticlesComponent,
    AuthorsComponent,
    RegistrationComponent,
    CreatePostComponent,
    RulesComponent,
    LoginComponent,
    DialogConfirmComponent,
    ImageDragDirective,
    UserpageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxPaginationModule,
    MatCardModule,
    BrowserAnimationsModule,
    MatButtonModule,
    AngularEditorModule,
  ],
  providers: [
      AuthService,
      { provide: HTTP_INTERCEPTORS, useClass: GlobalHttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
