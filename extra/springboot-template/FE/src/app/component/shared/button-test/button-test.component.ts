import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'button[appButtonTest]',
    standalone: true,
    imports: [CommonModule],
    template: `
        <button class="button is-primary">
            <span><i class="fa-solid fa-address-book"></i> Button</span>
        </button>
    `,
    styleUrls: [
        // './button-test.component.css', 
    ],
})
export class SharedButtonTestComponent {
}
