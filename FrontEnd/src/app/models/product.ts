import { Brand } from "./brand";

export class Product {
    constructor(product: Product | null = null) {
        if (product) {
            this.id = product.id;
            this.name = product.name;
            this.description = product.description;
            this.price = product.price;
            this.color = product.color;
            this.brand = product.brand;
            this.photoPath = product.photoPath + '?' + new Date().getTime();
            this.volume = product.volume;
            this.category = product.category;
        }
        else {
            this.id = '';
            this.name = '';
            this.description = '';
            this.brand = null;
            this.price = '';
            this.category = '';
            this.color = '#000000';
            this.volume = '';
            this.photoPath = '';
        }
    }

    id: string;
    name: string;
    description: string;
    brand: Brand | null;
    price: string;
    category: string;
    color: string;
    volume: string;
    photoPath: string;
}
