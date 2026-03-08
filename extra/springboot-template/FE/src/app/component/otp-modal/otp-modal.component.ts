import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { ComponentService } from '../../service/component.service';
import { delay } from '../../common/js/utils';

@Component({
    selector: 'app-otp-modal',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './otp-modal.component.html',
    styleUrls: ['./otp-modal.component.css', '../../common/css/loading-spinner.css'],
})
export class OtpModalComponent {
    private cdr = inject(ChangeDetectorRef);
    private authService = inject(AuthService);
    componentService = inject(ComponentService);

    otpValue = '';
    isLoading = false;

    async confirm(): Promise<void> {
        console.log('Checking OTP:', this.otpValue);
        if (!this.otpValue.trim()) return;
        this.isLoading = true;
        this.cdr.detectChanges();
        await delay(1000);
        // const isOTPOk = this.authService.checkOTP(this.otpValue.trim());
        if (this.otpValue.trim() !== '111111') {
            console.error('Invalid OTP');
            this.isLoading = false;
            this.otpValue = '';
            this.componentService.showNotiError('Invalid OTP');
            this.cdr.detectChanges();
            return;
        };
        this.authService.checkOTP(this.otpValue.trim());
        this.componentService.showNotiSuccess('Login successful');
        this.isLoading = false;
        this.otpValue = '';
        this.componentService.closeOtpModal();
        this.cdr.detectChanges();
    }

    cancel(): void {
        this.otpValue = '';
        this.componentService.closeOtpModal();
    }
}
