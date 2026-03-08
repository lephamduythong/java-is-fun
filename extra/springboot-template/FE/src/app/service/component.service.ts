import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ComponentService {
    isLogoutModalVisible = signal(false);
    isNotiHasError = signal(false);
    notiText = signal('');

    open(): void {
        this.isLogoutModalVisible.set(true);
    }

    close(): void {
        this.isLogoutModalVisible.set(false);
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
