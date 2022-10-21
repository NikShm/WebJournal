export class User {
    constructor(user: User) {
        this.login = user.login
        this.role = user.role;
    }

    login: string;
    role: string;
}