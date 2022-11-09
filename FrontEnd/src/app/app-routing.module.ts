import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PostsComponent } from './components/posts/posts.component';
import {PostComponent} from "./components/post/post.component";
import {ArticlesComponent} from './components/articles/articles.component';
import { AuthorsComponent } from './components/authors/authors.component';
import { RegistrationComponent } from './registration/registration.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'posts', component: PostsComponent},
  {path: 'post', component: PostComponent},
  {path: 'articles', component: ArticlesComponent},
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
