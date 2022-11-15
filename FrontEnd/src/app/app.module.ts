import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgxPaginationModule } from 'ngx-pagination';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularEditorModule } from '@kolkov/angular-editor';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { PostComponent } from './components/post/post.component';
import { PostsComponent } from './components/posts/posts.component';
import { AuthorsComponent } from './components/authors/authors.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { RulesComponent } from './components/rules/rules.component';
import { LoginComponent } from './components/login/login.component';
import { DialogConfirmComponent } from './components/dialog-confirm/dialog-confirm.component';
import { ProfileComponent } from './components/profile/profile.component';

import { GlobalHttpInterceptorService } from "./services/global-http-interceptor.service";
import { AuthService } from './services/auth.service';

import { ImageDragDirective } from './directives/image-drag.directive';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    PostComponent,
    PostsComponent,
    AuthorsComponent,
    RegistrationComponent,
    CreatePostComponent,
    RulesComponent,
    LoginComponent,
    DialogConfirmComponent,
    ImageDragDirective,
    ProfileComponent
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
