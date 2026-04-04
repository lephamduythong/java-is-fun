import { Component, inject } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { ComponentService } from '../../service/component.service';

@Component({
    selector: 'app-logout-modal',
    standalone: true,
    imports: [],
    templateUrl: './logout-modal.component.html',
    styleUrl: './logout-modal.component.css',
})
export class LogoutModalComponent {
    componentService = inject(ComponentService);
    private authService = inject(AuthService);

    confirm(): void {
        console.log('Logging out...');
        this.authService.logout();
        this.componentService.close();
    }

    cancel(): void {
        this.componentService.close();
    }
}
