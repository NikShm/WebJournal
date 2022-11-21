export class Post {
  constructor(post: Post | null = null) {
    if (post) {
      this.id = post.id;
      this.title = post.title;
      this.foreword = post.foreword;
      this.content = post.content;
      this.author_id = post.author_id;
    }
    else {
      this.id = '';
      this.title = '';
      this.foreword = '';
      this.content = '';
    }
  }
  id: string;
  title: string;
  foreword: string;
  content: string;
  author_id: bigint | undefined;

}
