export class PostList {

  constructor(post:PostList) {
    this.id = post.id;
    this.title = post.title;
    this.foreword = post.foreword;
    this.publishedAt = post.publishedAt;
  }

  id: string;
  title: string;
  foreword: string;
  publishedAt: Date;
}
