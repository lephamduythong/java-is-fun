import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-icon-test',
    standalone: true,
    imports: [CommonModule],
    template: `
        <span><i class="fa-regular fa-address-card"></i></span>
    `,
    styleUrls: [
        // './icon-test.component.css', 
    ],
})
export class SharedIconTestComponent {
}
