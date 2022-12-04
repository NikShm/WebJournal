export class Author {
  constructor(author:Author | null = null) {
    if (author) {
      this.id = author.id;
      this.username = author.username;
      this.followers = author.followers;
      this.bio = author.bio;
    }
    else {
      this.id = '';
      this.username = '';
      this.followers = 0;
      this.bio = '';
    }
  }

  id: string;
  username: string;
  followers: number;
  bio: string;
}
