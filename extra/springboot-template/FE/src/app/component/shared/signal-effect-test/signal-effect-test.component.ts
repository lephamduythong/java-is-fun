import { Component, effect, ElementRef, signal, viewChild, ViewChild, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-signal-effect-test',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <form (ngSubmit)="onSubmit(form)" #form>
            <div class="control mt-3">
                <input class="input is-normal" type="text" placeholder="Some value" />
            </div>
            <div class="control mt-3">
                <button class="button is-link">Submit</button>
            </div>
        </form>
    `,
    styles: `
    `,
    encapsulation: ViewEncapsulation.None,
    host: {
    }
})
export class SharedSignalEffectTestComponent {
    
    fieldVal = signal('');

    constructor() {
        effect(() => {
            console.log('effect - fieldVal changed:', this.fieldVal());
        });
    }

    onSubmit(form: HTMLFormElement) {
        this.fieldVal.set((form.elements[0] as HTMLInputElement).value);
        form.reset();
        console.log('Form submitted, fieldVal:', this.fieldVal());
    }
}
