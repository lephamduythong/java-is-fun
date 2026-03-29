import { Injectable, signal } from "@angular/core";
import { ItemTest } from "../component/learning-test/item-test/item-test.model";

@Injectable({providedIn: "root"})
export class LearningTestService {
    
    items = signal<ItemTest[]>([]);

    addItem(item: ItemTest) {
        console.log('Adding item in service:', item);
        this.items.update(items => [...items, item]);
    }

    removeItem(id: number) {
        console.log('Removing item in service with id:', id);
        this.items.update(items => items.filter(item => item.id !== id));
    }
}