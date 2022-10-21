import {Injectable} from '@angular/core';
import {Product} from "../models/product";
@Injectable({
  providedIn: 'root'
})
export class CartService {
  products: Product[] = [];
  done: boolean = false;

  addToCart(product: Product) {
    this.done = true;
    for(let i = 0; i < this.products.length; i++){
      if (this.products[i].id == product.id){
        this.done = false;
      }
    }

    if (!this.done){
      return this.products;
    }
    else {
      this.products.push(product);
      return localStorage.setItem('products', JSON.stringify(this.products));
    }
  }

  delFromCart(index:any) {
    this.products.splice(index, 1);
    localStorage.setItem('products', JSON.stringify(this.products));
    return JSON.parse(localStorage.getItem('products') || '[]');
  }

  getItems() {
    return this.products = JSON.parse(localStorage.getItem('products') || '[]');
  }

  clearCart() {
    this.products.splice(0, this.products.length);
    return localStorage.removeItem("products");
  }
  constructor() { }
}
