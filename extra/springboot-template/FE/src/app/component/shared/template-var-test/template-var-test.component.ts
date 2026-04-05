import { Component, ElementRef, viewChild, ViewChild, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-template-var-test',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <form (ngSubmit)="onSubmit(form)" #form>
            <div class="control mt-3">
                <input class="input is-normal" type="text" placeholder="Username" />
            </div>
            <div class="control mt-3">
                <input class="input is-normal" type="password" placeholder="Password" />
            </div>
            <div class="control mt-3">
                <button class="button is-link">Submit</button>
            </div>
        </form>
    `,
    styles: `
    `,
    encapsulation: ViewEncapsulation.None,
    host: {
    }
})
export class SharedTemplateVarTestComponent {
    @ViewChild('form') form2!: ElementRef<HTMLFormElement>; // 2
    private form3 = viewChild.required<ElementRef<HTMLFormElement>>('form'); // 3

    onSubmit(form: HTMLFormElement) {
        console.dir(form);

        console.log('Username 1:', (form.elements[0] as HTMLInputElement).value);
        console.log('Password 1:', (form.elements[1] as HTMLInputElement).value);

        console.log('Username 2:', (this.form2.nativeElement.elements[0] as HTMLInputElement).value);
        console.log('Password 2:', (this.form2.nativeElement.elements[1] as HTMLInputElement).value);

        console.log('Username 3:', (this.form3().nativeElement.elements[0] as HTMLInputElement).value);
        console.log('Password 3:', (this.form3().nativeElement.elements[1] as HTMLInputElement).value);

        console.log('Form submitted');

        form.reset();
    }
}
