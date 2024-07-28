package org.slaega.family_secret.passwordless.util;

public class AuthErrors {
    public static final Error INVALID_CREDENTIALS = new Error("AUTH-001", "Invalid credentials.");
    public static final Error USER_NOT_FOUND = new Error("AUTH-002", "User not found.");
    public static final Error EMAIL_ALREADY_EXISTS = new Error("AUTH-003", "Email already exists.");
    public static final Error INVALID_TOKEN = new Error("AUTH-004", "Invalid token.");
    public static final Error TOKEN_EXPIRED = new Error("AUTH-005", "Token expired.");
    public static final Error UNAUTHORIZED = new Error("AUTH-006", "Unauthorized.");
    public static final Error ACCESS_DENIED = new Error("AUTH-007", "Access denied.");
    public static final Error USER_NOT_VERIFIED = new Error("AUTH-008", "User not verified.");
    public static final Error USER_ALREADY_VERIFIED = new Error("AUTH-009", "User already verified.");
    public static final Error INVALID_REFRESH_TOKEN = new Error("AUTH-010", "Invalid refresh token provided.");
    public static final Error UNKNOWN_ERROR = new Error("AUTH-099", "Unknown error.");

    public static class Error {
        private final String code;
        private final String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}

