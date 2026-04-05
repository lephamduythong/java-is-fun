import { Directive, effect, ElementRef, inject, input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { Permission } from '../model/auth.model';
import { AuthService } from '../../service/auth.service';

@Directive({ 
    selector: '[appLog]',
    standalone: true,
    host: {
        '(click)': 'onLog()'
    }
})
export class LogDirective {
    private elRef = inject(ElementRef);

    constructor() {}

    onLog(): void {
        console.log('Element clicked:', this.elRef.nativeElement);
    }
}