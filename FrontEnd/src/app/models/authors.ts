import { User } from "./user";

  export class Authors{
      constructor(authors: any){
      this.id = authors.id;
      this.username = authors.username;
      this.followers = authors.followers;
      }

      id : number;
      username : string;
      followers :number
}

