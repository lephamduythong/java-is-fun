import { Component, ElementRef, Host, HostBinding, HostListener, inject, input, signal, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-control-2-test',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div>
            <input 
                [class]="{
                    'button': true,
                    'is-primary': true, 
                    'custom-purple': currentStatus() === 'active'
                }" 
                [disabled]="currentStatus() !== 'active'" 
            />
        </div>
        <div>
            <p [style]="{ 
                'font-size': '48px',
                'color': currentStatus() === 'active' ? 'green' : 'red',
            }">
                Current Status: {{currentStatus()}}
            </p>
        </div>
    `,
    styles: `
        .custom-purple {
            text-align: left;
            background-color: purple !important;
        }
    `,
    encapsulation: ViewEncapsulation.None,
    host: {
    }
})
export class SharedControl2TestComponent {
    currentStatus = signal('disabled');  
}
