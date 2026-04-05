import { Directive, effect, inject, input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { Permission } from '../model/auth.model';
import { AuthService } from '../../service/auth.service';

@Directive({ 
    selector: '[appAuth]',
    standalone: true,
})
export class AuthDirective {
    userType = input.required<Permission>({ alias: 'appAuth' });
    authService = inject(AuthService);
    templateRef = inject(TemplateRef);
    viewContainerRef = inject(ViewContainerRef);

    constructor() { 
        effect(() => {
            const currUser = this.authService.currentUserTypeTest();
            const userType = this.userType();
            console.log('AuthDirective effect run, current user:', currUser, 'required user type:', userType);
            if (currUser === userType) {
                console.log('SHOW');
                this.viewContainerRef.createEmbeddedView(this.templateRef);
            } else {
                console.log('NOT SHOW');
                this.viewContainerRef.clear();
            }
        });    
    }
}