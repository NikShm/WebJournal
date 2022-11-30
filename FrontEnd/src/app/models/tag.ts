export class Tag {

  constructor(tag:Tag) {
    this.id = tag.id;
    this.name = tag.name;
    this.createdAt = tag.createdAt;
  }

  id: string;
  name: string;
  createdAt: Date;
}
