import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { type ItemTest } from '../item-test/item-test.model'
import { LearningTestService } from '../../../service/learning-test.service';

@Component({
    selector: 'app-new-item-test',
    standalone: false,
    templateUrl: './new-item-test.component.html',
})
export class NewItemTestComponent {
    @Output() saveNewItem = new EventEmitter<ItemTest>();
    private learningTestService = inject(LearningTestService);

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

    onNewItemSave2() {
        if (!this.newItemName.trim()) {
            console.log('New item name is empty, not saving.');
            return;
        }
        console.log('Saving new item with name:', this.newItemName);
        const newItem: ItemTest = {
            id: Math.floor(Math.random() * 1000000), // Generate a random ID
            name: this.newItemName,
        }
        this.learningTestService.addItem(newItem);
    }
}
