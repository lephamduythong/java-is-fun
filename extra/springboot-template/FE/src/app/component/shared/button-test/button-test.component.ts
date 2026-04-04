import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'button[appButtonTest]',
    standalone: true,
    imports: [CommonModule],
    template: `
        <button class="button is-primary">
            <span><ng-content select=".my-icon"></ng-content></span> &nbsp;
            <span><ng-content></ng-content></span>
        </button>
    `,
    styleUrls: [
        // './button-test.component.css', 
    ],
})
export class SharedButtonTestComponent {
}
