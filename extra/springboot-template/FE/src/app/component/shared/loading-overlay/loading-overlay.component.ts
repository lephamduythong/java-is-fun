import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreService } from '../../../service/store.service';

@Component({
    selector: 'app-loading-overlay',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './loading-overlay.component.html',
    styleUrls: ['./loading-overlay.component.css'],
})
export class LoadingOverlayComponent {
    storeService = inject(StoreService);
}
