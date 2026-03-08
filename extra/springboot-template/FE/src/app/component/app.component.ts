import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { ComponentService as ComponentService } from '../service/component.service';
import { LogoutModalComponent } from './logout-modal/logout-modal.component';
import { QrscanModalComponent } from './qrscan-modal/qrscan-modal.component';
import { delay } from '../common/utils';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [LogoutModalComponent, QrscanModalComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
})
export class AppComponent {
    private cdr = inject(ChangeDetectorRef);
    
    componentService = inject(ComponentService);
    authService = inject(AuthService);
    isLoading = false;

    get authState() {
        return this.authService.getAuthState();
    }

    async login(event: Event): Promise<void> {
        event.preventDefault();
        console.log('Logging in...');
        this.isLoading = true;
        await delay(1000);
        let isLoginFirstOk = this.authService.loginFirst('admin', '123456');
        if (!isLoginFirstOk) {
            console.error('Login failed: Invalid username or password');
            this.isLoading = false;
            this.cdr.detectChanges();
            return;
        }
        this.authService.checkOTP('123456');
        this.componentService.showNotiSuccess('Login successful');
        console.log('Login successful');
        this.isLoading = false;
        this.cdr.detectChanges();
    }

    logout(): void {
        this.componentService.open();
    }

    signUp(event: Event): void {
        event.preventDefault();
        console.log('Signing up...');
        this.authService.signUp('thongle', '654321');
        const qrMsg = this.generateRandomString(16);
        this.componentService.openQrModal(qrMsg);
    }

    toggleLoading(): void {
        this.isLoading = !this.isLoading;
    }

    async refresh(): Promise<void> {
        console.log('Refreshing data...');
        this.isLoading = true;
        await delay(1000);
        this.isLoading = false;
        this.cdr.detectChanges();
    }

    private generateRandomString(length: number): string {
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let result = '';
        for (let i = 0; i < length; i++) {
            result += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return result;
    }
}
