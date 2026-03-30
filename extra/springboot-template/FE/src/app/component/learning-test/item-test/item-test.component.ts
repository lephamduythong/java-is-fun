import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../../common/js/constants';
import { type ItemTest } from './item-test.model'
import { LearningTestService } from '../../../service/learning-test.service';

@Component({
    selector: 'app-item-test',
    standalone: false,
    templateUrl: './item-test.component.html',
})
export class ItemTestComponent {
    @Input({required: true}) itemTest!: ItemTest;

    @Output() itemClicked = new EventEmitter<number>();
    @Output() itemDeleted = new EventEmitter<number>();

    learningTestService = inject(LearningTestService);

    isSelected: boolean = false;

    onItemClick() {
        console.log(`Clicked item with id: ${this.itemTest.id} and name: ${this.itemTest.name}`);
        this.isSelected = !this.isSelected;
        this.itemClicked.emit(this.itemTest.id);
    }

    onItemDelete() {
        console.log(`Delete item with id: ${this.itemTest.id} and name: ${this.itemTest.name}`);
        this.itemDeleted.emit(this.itemTest.id);
    }

    onItemDelete2() {
        console.log(`Delete item with id: ${this.itemTest.id} and name: ${this.itemTest.name}`);
        this.learningTestService.removeItem(this.itemTest.id);
    }
}
