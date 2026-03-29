import { NgModule } from '@angular/core';
import { LearningTestComponent } from './learning-test/learning-test.component';
import { NewItemTestComponent } from './learning-test/new-item-test/new-item-test.component';
import { ItemTestComponent } from './learning-test/item-test/item-test.component';

@NgModule({
    declarations: [
        LearningTestComponent,
        ItemTestComponent, 
        NewItemTestComponent, 
    ],
    imports: [],
    exports: [
        LearningTestComponent,
        ItemTestComponent, 
        NewItemTestComponent, 
    ]
})
export class LearningTestModule {}