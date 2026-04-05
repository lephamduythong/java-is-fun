import { Component, ElementRef, Host, HostBinding, HostListener, inject, input, signal, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-template-var-test',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <form (ngSubmit)="onSubmit(usernameEl, passwordEl)">
            <div class="control mt-3">
                <input #usernameEl class="input is-normal" type="text" placeholder="Username" />
            </div>
            <div class="control mt-3">
                <input #passwordEl class="input is-normal" type="password" placeholder="Password" />
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
    onSubmit(usernameEl: HTMLInputElement, passwordEl: HTMLInputElement) {
        console.dir(usernameEl);
        console.dir(passwordEl);

        console.log('Username:', usernameEl.value);
        console.log('Password:', passwordEl.value);
        console.log('Form submitted');
    }
}
