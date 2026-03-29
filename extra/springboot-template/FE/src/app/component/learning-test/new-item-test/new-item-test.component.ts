import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { type ItemTest } from '../item-test/item-test.model'

@Component({
    selector: 'app-new-item-test',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './new-item-test.component.html',
})
export class NewItemTestComponent {
    @Output() saveNewItem = new EventEmitter<ItemTest>();

    newItemName: string = '';

    onNewItemSave() {
        if (!this.newItemName.trim()) {
            console.log('New item name is empty, not saving.');
            return;
        }
        console.log('Saving new item with name:', this.newItemName);
        const newItem: ItemTest = {
            id: Math.floor(Math.random() * 1000000), // Generate a random ID
            name: this.newItemName,
        }
        this.saveNewItem.emit(newItem);
    }
}
