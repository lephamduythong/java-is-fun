import { AfterContentChecked, AfterViewChecked, AfterViewInit, Component, DoCheck, ElementRef, Host, HostBinding, HostListener, inject, Input, input, OnChanges, OnDestroy, OnInit, signal, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import * as Math from 'mathjs';

@Component({
    selector: 'app-control-3-test',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div>
            <label>Control 3 Test</label>
            <input [(ngModel)]="textTest3" class="input is-primary" type="text" placeholder="Type something..." />
        </div>
    `,
    styles: `
        
    `,
    encapsulation: ViewEncapsulation.None,
    host: {

    }
})
export class SharedControl3TestComponent implements
    OnInit, OnChanges, DoCheck, AfterViewInit, AfterViewChecked, AfterContentChecked, OnDestroy {
        
    @Input({required: true}) textTest3!: string;

    constructor() {
        console.log(`${SharedControl3TestComponent.name} - constructor`);
        console.log('Initial textTest3 value in constructor:', this.textTest3);
    }

    ngOnInit(): void {
        console.log(`${SharedControl3TestComponent.name} - ngOnInit`);
        console.log('Initial textTest3 value in ngOnInit:', this.textTest3);
    }

    ngDoCheck(): void {
        console.log(`${SharedControl3TestComponent.name} - ngDoCheck`);
    }

    ngAfterContentChecked(): void {
        console.log(`${SharedControl3TestComponent.name} - ngAfterContentChecked`);
    }

    ngAfterViewInit(): void {
        console.log(`${SharedControl3TestComponent.name} - ngAfterViewInit`);
    }

    ngAfterViewChecked(): void {
        console.log(`${SharedControl3TestComponent.name} - ngAfterViewChecked`);
    }

    ngOnChanges(changes: SimpleChanges): void {
        console.log(`${SharedControl3TestComponent.name} - ngOnChanges: `);
        console.log(changes);
    }

    ngOnDestroy(): void {
        console.log(`${SharedControl3TestComponent.name} - ngOnDestroy`);
    }
}
