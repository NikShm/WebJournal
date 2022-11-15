export class User {
    constructor(user: User) {
        this.id = user.id;
        this.username = user.username;
        this.bio = user.bio;
        this.followers = user.followers;
        this.following = user.following;
        this.posts = user.posts;
    }

    id: string;
    username: string;
    bio: string;
    followers: number;
    following: number;
    posts: number;
}