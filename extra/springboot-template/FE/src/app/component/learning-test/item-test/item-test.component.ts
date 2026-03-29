import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Constants from '../../../common/js/constants';

@Component({
    selector: 'app-item-test',
    standalone: true,
    imports: [FormsModule, NgIf],
    templateUrl: './item-test.component.html',
})
export class ItemTestComponent {
    @Input({required: true}) id!: number;
    @Input({required: true}) name!: string;
    @Output() itemClicked = new EventEmitter<number>();

    isSelected: boolean = false;

    onItemClick() {
        console.log(`Clicked item with id: ${this.id} and name: ${this.name}`);
        this.isSelected = !this.isSelected;
        this.itemClicked.emit(this.id);
    }
}
