// import 'zone.js';
// import { bootstrapApplication } from '@angular/platform-browser';
// import { AppComponent } from './component/app.component';

// bootstrapApplication(AppComponent).catch(err => console.error(err));

import { platformBrowserDynamic } from "@angular/platform-browser-dynamic";
import { AppModule } from "./component/app.module";

platformBrowserDynamic().bootstrapModule(AppModule).catch((err: any) => console.error(err));   
