import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgxPaginationModule} from 'ngx-pagination';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AngularEditorModule} from '@kolkov/angular-editor';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatInputModule} from "@angular/material/input";
import {AutocompleteLibModule} from "angular-ng-autocomplete";
import {MatSortModule} from "@angular/material/sort";

import {AppComponent} from './app.component';
import {HomeComponent} from './components/home/home.component';
import {HeaderComponent} from './shared/components/header/header.component';
import {FooterComponent} from './shared/components/footer/footer.component';
import {PostComponent} from './components/post/post.component';
import {PostsComponent} from './components/posts/posts.component';
import {AuthorsComponent} from './components/authors/authors.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {CreatePostComponent} from './components/create-post/create-post.component';
import {RulesComponent} from './components/rules/rules.component';
import {LoginComponent} from './components/login/login.component';
import {DialogConfirmComponent} from './components/dialog-confirm/dialog-confirm.component';
import {NewsComponent} from "./components/news/news.component";
import {ProfileComponent} from './components/profile/profile.component';
import { PostInfoComponent } from './components/post-info/post-info.component';
import { ProfileDetailsComponent } from './components/profile/profile-details/profile-details.component';
import { ProfileEditComponent } from './components/profile/profile-edit/profile-edit.component';

import {GlobalHttpInterceptorService} from "./services/global-http-interceptor.service";

import { ImageDragDirective } from './directives/image-drag.directive';
import { BirthDateValidatorDirective } from './directives/birth-date-validator.directive';
import { FileValidatorDirective } from './directives/file-validator.directive';
import { ErrorComponent } from './components/error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    PostComponent,
    PostsComponent,
    NewsComponent,
    AuthorsComponent,
    RegistrationComponent,
    CreatePostComponent,
    RulesComponent,
    LoginComponent,
    DialogConfirmComponent,
    ImageDragDirective,
    ProfileComponent,
    BirthDateValidatorDirective,
    PostInfoComponent,
    ProfileDetailsComponent,
    ProfileEditComponent,
    FileValidatorDirective,
    ErrorComponent
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
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatInputModule,
    AutocompleteLibModule,
    MatSortModule,
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
    {provide: HTTP_INTERCEPTORS, useClass: GlobalHttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
