import { Component, effect, ElementRef, signal, viewChild, ViewChild, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Item {
    id: number;
    name: string;
}

@Component({
    selector: 'app-for-loop-test',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <table class="table">
            <thead>
                <tr>
                    <th><abbr title="Position">#</abbr></th>
                </tr>
            </thead>
            <tbody>
                @for (item of list; track item.id) {
                    <tr>
                        <th>{{item.name}}</th>
                    </tr>
                } @empty {
                    <tr>
                        <th>No items available</th>
                    </tr>
                }
            </tbody>
        </table>
    `,
    styles: `
    `,
    encapsulation: ViewEncapsulation.None,
    host: {
    }
})
export class SharedForLoopTestComponent {
    list: Item[] = [];
}
