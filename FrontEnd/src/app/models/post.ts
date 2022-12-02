import {Tag} from "./tag";
import {Author} from "./author";

export class Post {
  constructor(post: Post | null = null) {
    if (post) {
      this.id = post.id;
      this.title = post.title;
      this.foreword = post.foreword;
      this.content = post.content;
      this.author = new Author(post.author);
      this.publishedAt = post.publishedAt;
      this.likes = post.likes;
      this.tags = post.tags.map((tag:Tag) => {
        return new Tag(tag)
      })
    }
    else {
      this.id = '';
      this.title = '';
      this.foreword = '';
      this.content = '';
      this.author = new Author();
      this.likes = 0;
    }
  }
  id: string;
  title: string;
  foreword: string;
  content: string;
  author: Author;
  publishedAt: Date | undefined;
  tags: Tag[] = [];
  likes: number;
}
