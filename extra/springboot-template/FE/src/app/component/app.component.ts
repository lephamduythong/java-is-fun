import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { ComponentService as ComponentService } from '../service/component.service';
import { LogoutModalComponent } from './logout-modal/logout-modal.component';
import { OtpModalComponent } from './otp-modal/otp-modal.component';
import { QrscanModalComponent } from './qrscan-modal/qrscan-modal.component';
import { delay } from '../common/js/utils';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [FormsModule, LogoutModalComponent, OtpModalComponent, QrscanModalComponent],
    templateUrl: './app.component.html',
    styleUrls: [
        './app.component.css', 
        '../common/css/bell.css',
        '../common/css/loading-spinner.css'
    ],
})
export class AppComponent {
    private cdr = inject(ChangeDetectorRef);
    
    componentService = inject(ComponentService);
    authService = inject(AuthService);
    isLoading = false;
    username = '';
    password = '';

    get authState() {
        return this.authService.getAuthState();
    }

    async login(event: Event): Promise<void> {
        event.preventDefault();
        console.log('Logging in...');
        this.isLoading = true;
        await delay(1000);

        // Show OTP if firstLogin passed
        let isLoginFirstOk = this.authService.loginFirst(this.username, this.password);
        if (!isLoginFirstOk) {
            console.error('Login failed: Invalid username or password');
            this.componentService.showNotiError('Invalid username or password');
            this.isLoading = false;

            this.cdr.detectChanges();
            return;
        }

        this.isLoading = false;
        this.componentService.openOtpModal();

        this.cdr.detectChanges();
    }

    logout(): void {
        this.componentService.open();
    }

    async signUp(event: Event): Promise<void> {
        event.preventDefault();
        console.log('Signing up... with username: ' + this.username);
        const isOk = this.authService.signUp(this.username, this.password);
        await delay(500);
        if (!isOk) {
            this.componentService.showNotiError('User is existing');
            this.cdr.detectChanges();
            return;
        }  
        const issuer = 'VIB';
        const account = 'thong.lepham@vib.com.vn';
        const secret = 'Y36SEEQAQOGCLT5F5HZIDMQF5YWMHZXB';
        const qrMsg = `otpauth://totp/${issuer}:${account}?secret=${secret}&issuer=${issuer}`;
        this.componentService.openQrModal(qrMsg);
        this.cdr.detectChanges();
        return;
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
