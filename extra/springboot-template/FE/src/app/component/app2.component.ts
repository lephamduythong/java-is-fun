import { Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector: 'app-root-2',
    standalone: true,
    imports: [],
    encapsulation: ViewEncapsulation.ShadowDom,
    styles: `
        @import url('../common/css/picnic.css');
    `,
    template: `
        <div class="">
            
        </div>
    `,
})
export class App2Component {

}
