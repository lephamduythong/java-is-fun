import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { LearningTestComponent } from './learning-test/learning-test.component';
import { FormsModule } from '@angular/forms';
import { LogoutModalComponent } from './logout-modal/logout-modal.component';
import { OtpModalComponent } from './otp-modal/otp-modal.component';
import { QrscanModalComponent } from './qrscan-modal/qrscan-modal.component';
import { ItemTestComponent } from './learning-test/item-test/item-test.component';
import { NewItemTestComponent } from './learning-test/new-item-test/new-item-test.component';
import { SharedAdvertisementTestComponent } from './shared/advertisement-test/advertisement-test.component';
import { DatePipe } from '@angular/common';
import { LearningTestModule } from './learning-test.module';
import { AuthModule } from './auth.module';

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent, 
    ],
    imports: [
        BrowserModule, 
        FormsModule,
        DatePipe,
        SharedAdvertisementTestComponent,
        LearningTestModule,
        AuthModule,
    ],
    exports: [],
})
export class AppModule {}