package com.example.demo.auth;

import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtUtil {
    // Use a secure, long key (at least 32 chars)
    private static final String SECRET = "my-demo-secret-key-1234567890!@#";
    public static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
}
