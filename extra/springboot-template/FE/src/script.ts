import { login, logout, getAuthState } from "./auth.js";

// Entry point
console.log("Hello World!");
console.log("Auth state:", getAuthState());

// Example usage
login("admin", "sample-token-123");
console.log("After login:", getAuthState());

logout();
console.log("After logout:", getAuthState());

// Add event listener to the login button
document.getElementById("btn-login")?.addEventListener("click", () => {
    login("admin", "sample-token-123");
    console.log("After login:", getAuthState());
});
