package com.example.demo.auth;

import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtUtil {
    // Use a secure, long key (at least 32 chars)
    //set a hard code secret
    private static final String SECRET = "my-demo-secret-key-1234567890!@#";
    //convert the secret into key, use for token last part
    public static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
}
