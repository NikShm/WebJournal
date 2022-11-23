import {Post} from "./post";

export class Page {
  constructor(products: Post[], pageCount : number, page : number, pageSize: number, totalItem:number) {
    this.products = products;
    this.pageCount = pageCount;
    this.page = page;
    this.pageSize = pageSize;
    this.totalItem= totalItem;
  }
  products: Post[] = [];
  pageCount : number;
  page : number;
  pageSize : number;
  totalItem:number;
}
