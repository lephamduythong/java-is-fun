import { InjectionToken } from '@angular/core';

export const OPTIONS = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
];

export const OPTIONS_TOKEN = new InjectionToken<typeof OPTIONS>('OPTIONS', {
    providedIn: null,
    factory: () => OPTIONS,
});