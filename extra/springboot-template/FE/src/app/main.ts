// import 'zone.js';
// import { bootstrapApplication } from '@angular/platform-browser';
// import { AppComponent } from './component/app.component';

// bootstrapApplication(AppComponent).catch(err => console.error(err));

import 'zone.js';
import { bootstrapApplication } from '@angular/platform-browser';
import { App2Component } from './component/app2.component';
import { HttpResponse, provideHttpClient, withInterceptors } from '@angular/common/http';
import { catchError, map, tap, throwError } from 'rxjs';

function loggingHttpInterceptor(req: any, next: any) {
    const modifiedReq = req.clone({
        setHeaders: { 'CUSTOM-HEADER': 'CUTE' }
    });
    console.log('HTTP Request:', modifiedReq);
    return next(modifiedReq).pipe(
        tap((event: any) => {
            if (event instanceof HttpResponse) {
                console.log('HTTP Response:', event);
            }
        }),
        catchError((error: any) => {
            console.error('HTTP Error:', error);
            return throwError(() => error);
        })
    );  
}

bootstrapApplication(App2Component, {
    providers: [provideHttpClient(withInterceptors([loggingHttpInterceptor]))],
}).catch(err => console.error(err));
