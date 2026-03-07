import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NgIf],
  template: `
    <section class="section">
      <div class="container">
        <h1 class="title">Bulma Buttons</h1>

        <!-- Auth status -->
        <div *ngIf="authState.isAuthenticated" class="notification is-success mb-4">
          Đã đăng nhập với tên <strong>{{ authState.username }}</strong>
        </div>
        <div *ngIf="!authState.isAuthenticated" class="notification is-warning mb-4">
          Chưa đăng nhập
        </div>

        <!-- Colors -->
        <div class="buttons">
          <button class="button is-primary" (click)="login()">Login</button>
          <button class="button is-link">Link</button>
          <button class="button is-info">Info</button>
          <button class="button is-success">Success</button>
          <button class="button is-warning">Warning</button>
          <button class="button is-danger" (click)="logout()">Logout</button>
        </div>

        <!-- Sizes -->
        <div class="buttons">
          <button class="button is-small">Small</button>
          <button class="button">Normal</button>
          <button class="button is-medium">Medium</button>
          <button class="button is-large">Large</button>
        </div>

        <!-- Styles -->
        <div class="buttons">
          <button class="button is-primary is-outlined">Outlined</button>
          <button class="button is-primary is-rounded">Rounded</button>
          <button class="button is-primary" [class.is-loading]="isLoading" (click)="toggleLoading()">Loading</button>
          <button class="button" disabled>Disabled</button>
        </div>
      </div>
    </section>
  `,
})
export class AppComponent {
  private authService = inject(AuthService);
  isLoading = false;

  get authState() {
    return this.authService.getAuthState();
  }

  login(): void {
    this.authService.login('admin', 'sample-token-123');
  }

  logout(): void {
    this.authService.logout();
  }

  toggleLoading(): void {
    this.isLoading = !this.isLoading;
  }
}
