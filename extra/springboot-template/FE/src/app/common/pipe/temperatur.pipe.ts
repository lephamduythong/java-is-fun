import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'temp',
    standalone: true
})
export class TemperaturePipe implements PipeTransform {

    // Default implementation for any type of value
    // transform(value: any, ...args: any[]): any {
    //     return value + '°C';
    // }

    transform(value: string | number | null, inputType: 'cel' | 'fah', outputType: 'cel' | 'fah'): string {
        if (value === null || value === undefined) {
            return '';
        }
        
        let val: number;
        if (typeof value === 'string') {
            val = parseFloat(value);
        } else {
            val = value;
        }

        // If input and output types are the same, no conversion needed
        if (inputType === outputType) {
            return val + (outputType === 'cel' ? '°C' : '°F');
        }

        let outputTemp: number;
        let unit: string;

        // Convert based on input and output types
        if (inputType === 'cel' && outputType === 'fah') {
            // Celsius to Fahrenheit: (C × 9/5) + 32
            outputTemp = val * (9 / 5) + 32;
            unit = '°F';
        } else if (inputType === 'fah' && outputType === 'cel') {
            // Fahrenheit to Celsius: (F - 32) × 5/9
            outputTemp = (val - 32) * (5 / 9);
            unit = '°C';
        } else {
            // Fallback (shouldn't reach here)
            outputTemp = val;
            unit = outputType === 'cel' ? '°C' : '°F';
        }

        return outputTemp.toFixed(1) + unit;
    }
}