import { login, logout, getAuthState } from "./authState.js";

// Entry point
console.log("Hello World!");
console.log("Auth state:", getAuthState());

// Example usage
login("admin", "sample-token-123");
console.log("After login:", getAuthState());

logout();
console.log("After logout:", getAuthState());
