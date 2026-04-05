import { ChangeDetectorRef, Component, computed, inject, Input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../common/js/constants';
import { generateRandomString } from '../../common/js/utils';
import { ItemTestComponent } from "./item-test/item-test.component";
import { NewItemTestComponent } from './new-item-test/new-item-test.component';
import { ItemTest } from './item-test/item-test.model';
import { SharedAdvertisementTestComponent } from '../shared/advertisement-test/advertisement-test.component';
import { LearningTestService } from '../../service/learning-test.service';
import { SharedButtonTestComponent } from "../shared/button-test/button-test.component";
import { SharedControlTestComponent } from "../shared/control-test/control-test.component";
import { SharedControl2TestComponent } from "../shared/control-test/control-test-2.component";
import { SharedControl3TestComponent } from "../shared/control-test/control-test-3.component";

@Component({
    selector: 'app-learning-test',
    standalone: true,
    imports: [
    FormsModule,
    ItemTestComponent,
    NewItemTestComponent,
    SharedAdvertisementTestComponent,
    SharedAdvertisementTestComponent,
    SharedButtonTestComponent,
    SharedControlTestComponent,
    SharedControl2TestComponent,
    SharedControl3TestComponent
],
    templateUrl: './learning-test.component.html',
    styleUrls: [
        './learning-test.component.css', 
    ],
})
export class LearningTestComponent {
    private cdr = inject(ChangeDetectorRef);
    
    learningTestService = inject(LearningTestService);

    items: ItemTest[] = [];

    textTest1 = signal('test1');
    textTest2 = computed(() => this.textTest1() + ' computed');
    // textTest2 = this.textTest1 + ' computed'; // => this will not work, because textTest1 is a signal, not a string

    isAddingNewItem = false;

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
        this.isAddingNewItem = true;
    }

    onNewItemAdded(newItem: ItemTest) {
        console.log('New item received in parent component:', newItem);
        this.items.push(newItem);
        this.isAddingNewItem = false;
        console.log('New item added in parent component:', newItem);
    }

    textTest3 = signal('1.0');
    isTest3Visible = signal(true);

    onRandomNumberForTest3(): void {
        this.textTest3.set(Math.random().toString());
    }

    onDestroyTest3() {
        this.isTest3Visible.set(false);
    }
}
