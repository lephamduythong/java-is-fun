import { ChangeDetectorRef, Component, computed, inject, Input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
    selector: 'app-shared-advertisement-test',
    standalone: true,
    templateUrl: './advertisement-test.component.html',
    imports: [
        DatePipe,
    ],
    styleUrls: [
        // './advertisement-test.component.css', 
    ],
})
export class SharedAdvertisementTestComponent {
    private cdr = inject(ChangeDetectorRef);
    
    today = new Date();
}
