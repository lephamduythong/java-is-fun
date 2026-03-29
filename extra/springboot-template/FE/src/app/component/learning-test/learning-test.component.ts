import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Constants from '../../common/js/constants';

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

    get imgPath() {
        return `${Constants.IMG_STATIC_PATH}cat.jpg`;
    }
}
