import { AfterViewChecked, Component, ElementRef, inject, ViewChild } from '@angular/core';
import { ComponentService } from '../../service/component.service';
import QRCode from 'qrcode';

@Component({
    selector: 'app-qrscan-modal',
    standalone: true,
    imports: [],
    templateUrl: './qrscan-modal.component.html',
    styleUrl: './qrscan-modal.component.css',
})
export class QrscanModalComponent implements AfterViewChecked {
    componentService = inject(ComponentService);

    @ViewChild('qrCodeEl') canvasRef!: ElementRef<HTMLCanvasElement>;

    private lastQrMsg = '';

    ngAfterViewChecked(): void {
        const msg = this.componentService.qrMsg();
        if (this.canvasRef && msg && msg !== this.lastQrMsg) {
            this.lastQrMsg = msg;
            QRCode.toCanvas(this.canvasRef.nativeElement, msg, (error: any) => {
                if (error) console.error(error);
            });
        }
    }

    close(): void {
        this.componentService.closeQrModal();
    }
}
