import { NgModule } from '@angular/core';
import { OtpModalComponent } from './otp-modal/otp-modal.component';
import { LogoutModalComponent } from './logout-modal/logout-modal.component';
import { QrscanModalComponent } from './qrscan-modal/qrscan-modal.component';

@NgModule({
    declarations: [
        LogoutModalComponent,
        OtpModalComponent,
        QrscanModalComponent,
    ],
    imports: [],
    exports: [
        LogoutModalComponent,
        OtpModalComponent,
        QrscanModalComponent,
    ]
})
export class AuthModule {}