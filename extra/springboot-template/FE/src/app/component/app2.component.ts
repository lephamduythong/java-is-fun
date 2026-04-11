import { Component, inject, ViewEncapsulation } from '@angular/core';
import { StoreService } from '../service/store.service';
import { OPTIONS, OPTIONS_TOKEN } from '../common/js/LOV';

@Component({
    selector: 'app-root-2',
    standalone: true,
    imports: [],
    encapsulation: ViewEncapsulation.ShadowDom,
    styleUrls: ['app2.component.scss'],
    templateUrl: 'app2.component.html',
    providers: [{ provide: OPTIONS_TOKEN, useValue: OPTIONS }]
})
export class App2Component {
    storeService = inject(StoreService);
    options = inject(OPTIONS_TOKEN);
}
