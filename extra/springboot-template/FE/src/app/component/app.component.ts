import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, inject, ViewChild } from '@angular/core';
import { AuthService } from '../service/auth.service';
import QRCode from 'qrcode';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [],
    templateUrl: './app.component.html',
})
export class AppComponent implements AfterViewInit {
    private cdr = inject(ChangeDetectorRef);

    authService = inject(AuthService);
    isLoading = false;
    isShowQRScan = false;
    qrMsg = '';

    @ViewChild('qrCodeEl') canvasRef!: ElementRef<HTMLCanvasElement>;

    ngAfterViewInit(): void {
        if (!this.qrMsg) {
            this.generateQR(this.qrMsg);
        }
    }

    generateQR(msg: string): void {
        QRCode.toCanvas(this.canvasRef.nativeElement, msg, (error: any) => {
            if (error) console.error(error);
            else console.log('QR Code generated:', msg);
        });
    }

    get authState() {
        return this.authService.getAuthState();
    }

    login(event: Event): void {
        event.preventDefault();
        console.log('Logging in...');
        
        let isLoginFirstOk = this.authService.loginFirst('admin', '123456');
        if (!isLoginFirstOk) {
            console.error('Login failed: Invalid username or password');
            return;
        }
        this.authService.checkOTP('123456');
    }

    logout(): void {
        console.log('Logging out...');
        this.authService.logout();
    }

    signUp(event: Event): void {
        event.preventDefault();
        console.log('Signing up...');
        this.authService.signUp('thongle', '654321');
        // const qrMsg = this.authService.getQRScan('thongle');
        const qrMsg = this.generateRandomString(16);
        this.isShowQRScan = true;
        this.cdr.detectChanges();
        this.generateQR(qrMsg);
    }

    toggleLoading(): void {
        this.isLoading = !this.isLoading;
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
