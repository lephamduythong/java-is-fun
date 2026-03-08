import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ComponentService {
    isLogoutModalVisible = signal(false);
    isNotiHasError = signal(false);
    notiText = signal('');
    isQrModalVisible = signal(false);
    qrMsg = signal('');

    open(): void {
        this.isLogoutModalVisible.set(true);
    }

    close(): void {
        this.isLogoutModalVisible.set(false);
    }

    openQrModal(msg: string): void {
        this.qrMsg.set(msg);
        this.isQrModalVisible.set(true);
    }

    closeQrModal(): void {
        this.isQrModalVisible.set(false);
        this.qrMsg.set('');
    }

    showNotiSuccess(text: string): void {
        this.notiText.set(text);
        this.isNotiHasError.set(false);
        setTimeout(() => {
            this.notiText.set('');
        }, 3000);
    }

    showNotiError(text: string): void {
        this.notiText.set(text);
        this.isNotiHasError.set(true);
        setTimeout(() => {
            this.notiText.set('');
        }, 3000);
    }
}
