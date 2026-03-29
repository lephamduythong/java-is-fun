import { ChangeDetectorRef, Component, computed, inject, Input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
    selector: 'app-shared-advertisement-test',
    standalone: true,
    imports: [FormsModule, CommonModule, DatePipe],
    templateUrl: './advertisement-test.component.html',
    styleUrls: [
        // './advertisement-test.component.css', 
    ],
})
export class SharedAdvertisementTestComponent {
    private cdr = inject(ChangeDetectorRef);
    
    today = new Date();
}
