import { ChangeDetectorRef, Component, computed, inject, Input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-shared-advertisement-test',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './advertisement-test.component.html',
    styleUrls: [
        // './advertisement-test.component.css', 
    ],
})
export class SharedAdvertisementTestComponent {
    private cdr = inject(ChangeDetectorRef);
    
}
