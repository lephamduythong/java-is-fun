import { Injectable } from '@angular/core';

interface AuthState {
    // Login state
    isLoginFirstOk: boolean;
    isAuthenticated: boolean;
    accessToken: string | null;
    username: string | null;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
    private state: AuthState = {
        isLoginFirstOk: false,
        isAuthenticated: false,
        username: null,
        accessToken: null
    };

    signUp(username: string, pw: string): boolean {
        // Check if username already exists, if not create new user in Database
        if (username !== 'admin') {
            return true;
        }
        return false;
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
            isLoginFirstOk: true,
            isAuthenticated: false,
            username,
            accessToken: 'sample-token-123',
        };

        return true;
    }

    // Return access token if OTP is correct
    checkOTP(otp: string): void {
        const accessToken = '123456789abcdef';
        // Set access token if OTP is correct
        if (otp === '111111') {
            this.state.accessToken = accessToken;
            this.state.isAuthenticated = true;
        }
    }

    logout(): boolean {
        this.state = {
            isLoginFirstOk: false,
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
