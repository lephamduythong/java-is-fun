import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ComponentService {
    isLogoutModalVisible = signal(false);

    open(): void {
        this.isLogoutModalVisible.set(true);
    }

    close(): void {
        this.isLogoutModalVisible.set(false);
    }
}
