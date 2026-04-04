import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { LearningTestComponent } from './learning-test/learning-test.component';
import { FormsModule } from '@angular/forms';
import { LogoutModalComponent } from './logout-modal/logout-modal.component';
import { OtpModalComponent } from './otp-modal/otp-modal.component';
import { QrscanModalComponent } from './qrscan-modal/qrscan-modal.component';

@NgModule({
    declarations: [AppComponent],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule, 
        FormsModule, 
        LearningTestComponent,
        LogoutModalComponent,
        OtpModalComponent,
        QrscanModalComponent,
    ],
    exports: [],
})
export class AppModule {}