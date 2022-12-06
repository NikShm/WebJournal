import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PostsComponent } from './components/posts/posts.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import {PostComponent} from "./components/post/post.component";
import { AuthorsComponent } from './components/authors/authors.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RulesComponent } from './components/rules/rules.component';
import { LoginComponent } from './components/login/login.component';
import {SafeUrl} from "@angular/platform-browser";
import {NewsComponent} from "./components/news/news.component";
import { ProfileDetailsComponent } from './components/profile/profile-details/profile-details.component';
import { ProfileEditComponent } from './components/profile/profile-edit/profile-edit.component';

export interface FileHandle {
  file: File;
  url: SafeUrl;
}

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'posts', component: PostsComponent},
  {path: 'news-posts', component: NewsComponent},
  {path: 'post', component: PostComponent},
  {path: 'posts/:id', component: PostComponent},
  {path: 'rules', component: RulesComponent},
  {path: 'login', component: LoginComponent},
  {path: 'createPost', component: CreatePostComponent},
  {path: 'authors', component: AuthorsComponent},
  {path: 'authors/:username', component: ProfileComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'profile', component: ProfileEditComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    anchorScrolling: 'enabled',
    scrollPositionRestoration: 'enabled'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
