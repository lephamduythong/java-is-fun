import { Directive } from '@angular/core';

@Directive({ 
    selector: 'a[appSafeLink]',
    standalone: true,
    host: {
        '(click)': 'onConfirmLeavePage($event)'
    }
})
export class SafeLinkDirective {
    constructor() {
        console.log('SafeLinkDirective constructor');
    }

    onConfirmLeavePage(event: MouseEvent) { 
        const wantsToLeave = confirm('Are you sure you want to leave this page?');
        if (wantsToLeave) {
            return;
        }
        event.preventDefault();
    }
}