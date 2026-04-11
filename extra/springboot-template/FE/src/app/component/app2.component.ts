import { Component, DestroyRef, inject, OnInit, signal, ViewEncapsulation } from '@angular/core';
import { StoreService } from '../service/store.service';
import { OPTIONS, OPTIONS_TOKEN } from '../common/js/LOV';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Post } from '../common/model/post.model';
import { delay } from '../common/js/utils';
import { LoadingOverlayComponent } from './shared/loading-overlay/loading-overlay.component';
import _ from 'lodash';

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

    downloadProgress = signal<number | null>(null);
    downloadStatus = signal('');

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

    downloadFile() {
        const url = 'http://localhost:8080/test-api/file/download';

        this.downloadProgress.set(0);
        this.downloadStatus.set('Downloading...');

        const sub = this.httpClient.get(url, {
            responseType: 'blob',
            reportProgress: true,
            observe: 'events'
        }).subscribe({
            next: (event) => {
                if (event.type === HttpEventType.DownloadProgress) {
                    const percent = event.total
                        ? Math.round((event.loaded / event.total) * 100)
                        : 0;
                    this.downloadProgress.set(percent);
                } else if (event.type === HttpEventType.Response) {
                    const blob = event.body as Blob;
                    const a = document.createElement('a');
                    const objectUrl = URL.createObjectURL(blob);
                    a.href = objectUrl;
                    a.download = '100MB.bin';
                    a.click();
                    URL.revokeObjectURL(objectUrl);
                    this.downloadProgress.set(100);
                    this.downloadStatus.set('Download complete!');
                }
            },
            error: () => {
                this.downloadStatus.set('Error: Download failed.');
                this.downloadProgress.set(null);
            }
        });
        this.destroyRef.onDestroy(() => sub.unsubscribe());
    }

    async ngOnInit() {
        
    }
}
