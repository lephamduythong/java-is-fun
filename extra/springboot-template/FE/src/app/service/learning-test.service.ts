import { Injectable, signal, effect } from "@angular/core";
import { ItemTest } from "../component/learning-test/item-test/item-test.model";

@Injectable({providedIn: "root"})
export class LearningTestService {
    
    items = signal<ItemTest[]>([]);

    constructor() {
        this.loadItemsFromLocalStorage();
    }

    addItem(item: ItemTest) {
        console.log('Adding item in service:', item);
        this.items.update(items => [...items, item]);
        this.syncItemsToLocalStorage();
    }

    removeItem(id: number) {
        console.log('Removing item in service with id:', id);
        this.items.update(items => items.filter(item => item.id !== id));
        this.syncItemsToLocalStorage();
    }

    syncItemsToLocalStorage() {
        localStorage.setItem('learningTestItems', JSON.stringify(this.items()));
    }

    loadItemsFromLocalStorage() {
        const itemsJson = localStorage.getItem('learningTestItems');
        if (itemsJson) {
            const items = JSON.parse(itemsJson) as ItemTest[];
            this.items.set(items);
        }
    }
}