import { Component, Input, Output, EventEmitter, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../../common/js/constants';

@Component({
    selector: 'app-item-test',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './item-test.component.html',
})
export class ItemTestComponent {
    @Input({required: true}) id!: number;
    @Input({required: true}) name!: string;
    // @Output() itemClicked = new EventEmitter<number>();
    itemClicked = output<number>();

    onItemClick() {
        console.log(`Clicked item with id: ${this.id} and name: ${this.name}`);
        this.itemClicked.emit(this.id);
    }
}
