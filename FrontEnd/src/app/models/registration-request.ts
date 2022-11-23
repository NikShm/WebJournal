export class RegistrationRequest {
    constructor() {
        this.username = "";
        this.email = "";
        this.birthDate = null;
        this.password = "";
    }

    username: string;
    email: string;
    birthDate: Date | null;
    password: string;
}