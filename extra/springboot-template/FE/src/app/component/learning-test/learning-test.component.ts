import { ChangeDetectorRef, Component, computed, inject, Input, signal } from '@angular/core';
import { NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Constants from '../../common/js/constants';
import { generateRandomString } from '../../common/js/utils';
import { ItemTestComponent } from "./item-test/item-test.component";

@Component({
    selector: 'app-learning-test',
    standalone: true,
    imports: [FormsModule, ItemTestComponent, NgFor],
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
}
