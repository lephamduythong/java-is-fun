import { Component, Host, HostBinding, HostListener, input, signal, ViewEncapsulation } from '@angular/core';
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
        .control-test .custom-purple {
            background-color: purple !important;
        }
        // :host input,textarea {
        //     background-color: red !important;
        // }
    `,
    encapsulation: ViewEncapsulation.None,
    host: {
        class: 'control-test',
        '(click)': 'onHostClick2()' // This is recommended way to add host listener
    }
})
export class SharedControlTestComponent {
    // @HostBinding('class') className = 'control-test'; // Deprecated, Not recomeneded
    
    // @HostListener('click') onHostClick1() { // Deprecated, Not recomeneded
    //     console.log('Control clicked style 1');
    // }
    
    label = input.required<string>();

    onHostClick2() {
        console.log('Control clicked style 2');
    }
}
