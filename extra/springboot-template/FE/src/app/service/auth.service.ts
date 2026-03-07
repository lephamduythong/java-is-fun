import { Injectable } from '@angular/core';

interface AuthState {
    // Login state
    isAuthenticated: boolean;
    accessToken: string | null;
    username: string | null;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
    private state: AuthState = {
        isAuthenticated: false,
        username: null,
        accessToken: null
    };

    signUp(username: string, pw: string): boolean {
        // Check if username already exists, if not create new user in Database
        return true;
    }

    getQRScan(username: string): string {
        return "SOME CUTE THING";
    }

    loginFirst(username: string, pw: string): boolean {
        // Send login with username and password, if success continue to login with OTP
        if (username !== 'admin' || pw !== '123456') {
            return false;
        }

        // Set state
        this.state = {
            isAuthenticated: true,
            username,
            accessToken: 'sample-token-123',
        };

        return true;
    }

    // Return access token if OTP is correct
    checkOTP(otp: string): void {
        let accessToken = '123456789abcdef';
        // Set access token if OTP is correct
        if (otp === '123456') {
            this.state.accessToken = accessToken;
        }
    }

    logout(): boolean {
        this.state = {
            isAuthenticated: false,
            username: null,
            accessToken: null,
        };
        return true;
    }

    getAuthState(): AuthState {
        return { ...this.state };
    }
}
