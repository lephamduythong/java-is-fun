import { Component, input, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-control-test',
    standalone: true,
    imports: [CommonModule],
    template: `
        <label>{{label()}}</label>
        <ng-content select="input, textarea"></ng-content>
    `,
    styleUrls: [
        // './control-test.component.css', 
    ],
})
export class SharedControlTestComponent {
    label = input.required<string>();
}
