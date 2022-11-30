export class Author {
    constructor(author:Author | null = null) {
      if (author) {
        this.id = author.id;
        this.username = author.username;
        this.followers = author.followers;
      }
      else {
        this.id = '';
        this.username = '';
        this.followers = 0;
      }
  }

  id: string;
  username: string;
  followers: number;
}
