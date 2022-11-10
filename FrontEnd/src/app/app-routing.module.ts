import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PostsComponent } from './components/posts/posts.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import {PostComponent} from "./components/post/post.component";
import {ArticlesComponent} from './components/articles/articles.component';
import { AuthorsComponent } from './components/authors/authors.component';
import { RegistrationComponent } from './registration/registration.component';
import { RulesComponent } from './components/rules/rules.component';
import {LoginComponent} from "./login/login.component";
import {SafeUrl} from "@angular/platform-browser";

export interface FileHandle {
  file: File;
  url: SafeUrl;
}

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'posts', component: PostsComponent},
  {path: 'post', component: PostComponent},
  {path: 'rules', component: RulesComponent},
  {path: 'articles', component: ArticlesComponent},
  {path: 'login', component: LoginComponent},
  {path: 'createPost', component: CreatePostComponent},
  {path: 'authors', component: AuthorsComponent},
  {path: 'registration', component: RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    anchorScrolling: 'enabled',
    scrollPositionRestoration: 'enabled'
  })],
  exports: [RouterModule]
})


export class AppRoutingModule { }
