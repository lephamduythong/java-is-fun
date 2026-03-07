import { Injectable } from '@angular/core';

interface AuthState {
  isAuthenticated: boolean;
  pw: string | null;
  username: string | null;
  accessToken: string | null;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private state: AuthState = {
    isAuthenticated: false,
    pw: null,
    username: null,
    accessToken: null,
  };

  login(username: string, pw: string): void {
    this.state = {
      isAuthenticated: true,
      pw,
      username,
      accessToken: 'sample-token-123',
    };
  }

  logout(): void {
    this.state = {
      isAuthenticated: false,
      pw: null,
      username: null,
      accessToken: null,
    };
  }

  getAuthState(): AuthState {
    return { ...this.state };
  }
}
