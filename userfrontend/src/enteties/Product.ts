import {Category} from "./Category.ts";

export type Product = {
    id: number;
    name: string;
    articleNumber: number;
    category: Category;
    description: string;
    active: boolean;
    price: number;
    stock: number;

}