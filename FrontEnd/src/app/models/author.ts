export class Author {
    constructor(author:Author) {
    this.id = author.id;
    this.username = author.username;
    this.followers = author.followers;
    this.bio = author.bio;

  }

  id: number;
  username: string;
  followers: number;
  bio: string;
}
