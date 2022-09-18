export class Brand {
    constructor(brand: Brand | null = null) {
        if (brand) {
            this.id = brand.id;
            this.name = brand.name;
        }
        else {
            this.id = '';
            this.name = '';
        }
    }

    id: string;
    name: string;
}