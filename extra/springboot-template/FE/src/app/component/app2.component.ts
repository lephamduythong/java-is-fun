import { Component, DestroyRef, inject, OnInit, signal, ViewEncapsulation } from '@angular/core';
import { StoreService } from '../service/store.service';
import { OPTIONS, OPTIONS_TOKEN } from '../common/js/LOV';
import { HttpClient } from '@angular/common/http';
import { Post } from '../common/model/post.model';
import { delay } from '../common/js/utils';
import { LoadingOverlayComponent } from './shared/loading-overlay/loading-overlay.component';

@Component({
    selector: 'app-root-2',
    standalone: true,
    imports: [LoadingOverlayComponent],
    encapsulation: ViewEncapsulation.ShadowDom,
    styleUrls: ['app2.component.scss'],
    templateUrl: 'app2.component.html',
    providers: [{ provide: OPTIONS_TOKEN, useValue: OPTIONS }]
})
export class App2Component implements OnInit {
    storeService = inject(StoreService);
    options = inject(OPTIONS_TOKEN);
    private httpClient = inject(HttpClient);
    private destroyRef = inject(DestroyRef);

    postsShow = signal<Post[]>([]);
    appMessage = signal('Hello from App2Component!');

    async fetchPosts() {
        this.storeService.isLoading.set(true);
        this.appMessage.set('Fetching posts from API...');
        await delay(2000); // Simulate network delay
        const sub = this.httpClient.get<Post[]>('https://jsonplaceholder.typicode.com/posts').subscribe(
        {
            next: (response) => {
                console.log('Fetching posts from API...');
                console.log('Data received from API:', response);
                this.storeService.posts.set(response);
                console.log('Posts fetched successfully:', this.storeService.posts());
                // Get only first 10 posts to show
                this.postsShow.set(this.storeService.posts().slice(0, 10));
                this.appMessage.set('Success: Posts fetched successfully!');
            },
            error: (error) => {
                console.error('Error fetching posts:', error);
                this.storeService.isLoading.set(false);
                this.appMessage.set('Error: Failed to fetch posts. Please try again later.');
            },
            complete: () => {
                console.log('Fetch posts request completed.');
                this.storeService.isLoading.set(false);
            }
        });
        this.destroyRef.onDestroy(() => sub.unsubscribe());
    }

    async ngOnInit() {
        
    }
}
