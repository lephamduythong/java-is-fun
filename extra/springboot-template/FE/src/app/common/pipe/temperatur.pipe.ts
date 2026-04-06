import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'tempCtoF',
    standalone: true
})
export class TemperaturePipe implements PipeTransform {

    // Default implementation for any type of value
    // transform(value: any, ...args: any[]): any {
    //     return value + '°C';
    // }

    transform(value: string | number): any {
        let val: number;
        if (typeof value === 'string') {
            val = parseFloat(value);
        } else {
            val = value;
        }
        const outputTemp = val * (9 / 5) + 32; // Convert Celsius to Fahrenheit
        return outputTemp + '°F';
    }
}