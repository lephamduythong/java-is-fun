import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class LogService {
    constructor() { }
    log(message: string) {
        console.log((new Date().toLocaleDateString() + ' ' + new Date().toLocaleTimeString()) + ': ' + message);
    }
}