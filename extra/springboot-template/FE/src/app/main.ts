import 'zone.js';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './component/app.component';

bootstrapApplication(AppComponent).catch(err => console.error(err));
