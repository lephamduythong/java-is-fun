// Auth state management
interface AuthState {
  isAuthenticated: boolean;
  token: string | null;
  username: string | null;
}

const authState: AuthState = {
  isAuthenticated: false,
  token: null,
  username: null,
};

export function login(username: string, token: string): void {
  authState.isAuthenticated = true;
  authState.token = token;
  authState.username = username;
}

export function logout(): void {
  authState.isAuthenticated = false;
  authState.token = null;
  authState.username = null;
}

export function getAuthState(): AuthState {
  return { ...authState };
}
