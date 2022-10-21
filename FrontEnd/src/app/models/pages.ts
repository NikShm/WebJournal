import {Product} from "./product";

export class Page {
  constructor(products: Product[], pageCount : number, page : number, pageSize: number,totalItem:number) {
    this.products = products;
    this.pageCount = pageCount;
    this.page = page;
    this.pageSize = pageSize;
    this.totalItem= totalItem;
  }
  products: Product[] = [];
  pageCount : number;
  page : number;
  pageSize : number;
  totalItem:number;
}
