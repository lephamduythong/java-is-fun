import { ChangeDetectorRef, Component, computed, inject, Input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../common/js/constants';
import { generateRandomString } from '../../common/js/utils';
import { ItemTestComponent } from "./item-test/item-test.component";

@Component({
    selector: 'app-learning-test',
    standalone: true,
    imports: [FormsModule, ItemTestComponent],
    templateUrl: './learning-test.component.html',
    styleUrls: [
        './learning-test.component.css', 
    ],
})
export class LearningTestComponent {
    private cdr = inject(ChangeDetectorRef);

    items = Constants.DUMMY_LIST;

    textTest1 = signal('test1');
    textTest2 = computed(() => this.textTest1() + ' computed');
    // textTest2 = this.textTest1 + ' computed'; // => this will not work, because textTest1 is a signal, not a string

    get imgPath() {
        return `${Constants.IMG_STATIC_PATH}cat.jpg`;
    }

    onTest1() {
        console.log('test1');
        this.textTest1.set(generateRandomString(10));
    }

    onItemClick(id: number) {
        console.log(`Item clicked in parent component with id: ${id}`);
    }

    onItemDelete(id: number) {
        console.log(`Item delete in parent component with id: ${id}`);
        this.items = this.items.filter(item => item.id !== id);
    }

    onAddItem() {
        const newItem = {
            id: this.items.length > 0 ? Math.max(...this.items.map(item => item.id)) + 1 : 1,
            name: `Item ${this.items.length + 1}`,
        };
        this.items = [...this.items, newItem];
    }
}
