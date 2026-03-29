import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../common/js/constants';
import { generateRandomString } from '../../common/js/utils';

@Component({
    selector: 'app-learning-test',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './learning-test.component.html',
    styleUrls: [
        './learning-test.component.css', 
    ],
})
export class LearningTestComponent {
    private cdr = inject(ChangeDetectorRef);

    textTest1 = 'test1';

    get imgPath() {
        return `${Constants.IMG_STATIC_PATH}cat.jpg`;
    }

    onTest1() {
        console.log('test1');
        this.textTest1 = generateRandomString(10);
    }
}
