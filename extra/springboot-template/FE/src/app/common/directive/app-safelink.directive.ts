import { Directive, input } from '@angular/core';

@Directive({ 
    selector: 'a[appSafeLink]',
    standalone: true,
    host: {
        '(click)': 'onConfirmLeavePage($event)'
    }
})
export class SafeLinkDirective {
    queryParam = input('');

    constructor() {
        console.log('SafeLinkDirective constructor');
    }

    onConfirmLeavePage(event: MouseEvent) { 
        const target = event.target as HTMLAnchorElement;
        const wantsToLeave = confirm('Are you sure you want to leave this page?');
        const finalAddress = target.href + "search?q=" + this.queryParam();
        console.log('Address:', finalAddress);
        if (wantsToLeave) {
            (event.target as HTMLAnchorElement).href = finalAddress;
            return;
        }
        event.preventDefault();
    }
}