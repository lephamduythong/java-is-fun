import { Component, inject, ViewEncapsulation } from '@angular/core';
import { StoreService } from '../service/store.service';

@Component({
    selector: 'app-root-2',
    standalone: true,
    imports: [],
    encapsulation: ViewEncapsulation.ShadowDom,
    styleUrls: ['app2.component.scss'],
    templateUrl: 'app2.component.html',
    // providers: [StoreService]
})
export class App2Component {
    storeService = inject(StoreService);
}
