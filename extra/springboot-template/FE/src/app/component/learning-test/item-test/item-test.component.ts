import { Component, Input, input, computed } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../../common/js/constants';

@Component({
    selector: 'app-item-test',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './item-test.component.html',
})
export class ItemTestComponent {
    // @Input({required: true}) id!: number; // Input (Uppowercase I) is a function that returns a property decorator function. 
    // @Input({required: true}) name!: string;
    id = input.required<number>(); // input() (Lowercase i) is a function that returns a property decorator function.
    name = input.required<string>(); 
    computed = computed(() => `${this.id()} - ${this.name()}`);
}
