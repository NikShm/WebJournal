export class User {
    constructor(user: User) {
        this.id = user.id;
        this.username = user.username;
        this.bio = user.bio;
        this.followers = user.followers;
        this.following = user.following;
        this.email = user.email;
        this.role = user.role;
        this.isFollowing = user.isFollowing;
    }

    id: string;
    username: string;
    bio: string;
    followers: number;
    following: number;
    email: string;
    role: string;
    isFollowing: boolean;
}