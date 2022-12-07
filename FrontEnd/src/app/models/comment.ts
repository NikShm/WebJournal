import {Author} from "./author";

export class Comment {
  constructor(comment:Comment | null = null) {
    if (comment) {
      this.id = comment.id;
      this.author =  new Author(comment.author);
      this.text = comment.text;
      this.postId = comment.postId
    }
    else {
      this.id = '';
      this.author = new Author();
      this.text = "";
      this.postId = ''
    }
  }

  id: string;
  author: Author;
  text: string;
  postId:string;
}
