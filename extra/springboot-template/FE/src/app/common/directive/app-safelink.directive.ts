import { Directive, ElementRef, inject, input } from '@angular/core';

@Directive({ 
    selector: 'a[appSafeLink]',
    standalone: true,
    host: {
        '(click)': 'onConfirmLeavePage($event)'
    }
})
export class SafeLinkDirective {
    queryParam = input('');
    private hostElRef = inject<ElementRef<HTMLAnchorElement>>(ElementRef);

    constructor() {
        console.log('SafeLinkDirective constructor');
    }

    onConfirmLeavePage(event: MouseEvent) { 
        const target = event.target as HTMLAnchorElement;
        const wantsToLeave = confirm('Are you sure you want to leave this page?');
        const finalAddress = target.href + "search?q=" + this.queryParam();
        console.log('Address:', finalAddress);
        if (wantsToLeave) {
            this.hostElRef.nativeElement.href = finalAddress;
            return;
        }
        event.preventDefault();
    }
}