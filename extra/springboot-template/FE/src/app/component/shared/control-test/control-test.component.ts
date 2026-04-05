import { Component, input, signal, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-control-test',
    standalone: true,
    imports: [CommonModule],
    template: `
        <label>{{label()}}</label>
        <ng-content select="input, textarea"></ng-content>
    `,
    styles: `
        .custom-purple {
            background-color: purple !important;
        }
    `,
    encapsulation: ViewEncapsulation.None
})
export class SharedControlTestComponent {
    label = input.required<string>();
}
