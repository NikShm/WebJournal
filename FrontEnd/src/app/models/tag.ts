export class Tag {

  constructor(tag:Tag | null = null) {
    if(tag){
      this.id = tag.id;
      this.name = tag.name;
    }
    else {
      this.id = '';
      this.name = '';
    }

  }

  id: string;
  name: string;
}
