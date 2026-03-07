// Auth state management
interface AuthState {
  isAuthenticated: boolean;
  pw: string | null;
  username: string | null;
  accessToken: string | null;
}

const authState: AuthState = {
  isAuthenticated: false,
  pw: null,
  username: null,
  accessToken: null,
};

export function login(username: string, pw: string): void {
  authState.isAuthenticated = true;
  authState.pw = pw;
  authState.username = username;
  authState.accessToken = "sample-token-123"; // Simulate token generation
}

export function logout(): void {
  authState.isAuthenticated = false;
  authState.pw = null;
  authState.username = null;
  authState.accessToken = null;
}

export function getAuthState(): AuthState {
  return { ...authState };
}
