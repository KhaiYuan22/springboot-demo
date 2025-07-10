package com.example.demo.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;

@RestController//Set class as REST API controller
@RequestMapping("/auth")// All routes in this controller start with /auth
public class AuthController {

    // Use a secure key in production!(No Use)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);//Get the key by auto generated SHA256(Way to generate) everytime start Currently no use

    @PostMapping("/token")//set a mapping to allow access /auth/token
    public ResponseEntity<?> getToken(@RequestBody LoginRequest login) {//get token from login request
        if ("user".equals(login.getUsername()) && "pass".equals(login.getPassword())) {//if it is match user and password
            String jwt = Jwts.builder()//build the token with:
                    .setSubject(login.getUsername())//add username
                    .setIssuedAt(new Date())//add get token date
                    .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // only can use within 1 hour
                    .signWith(JwtUtil.KEY)//sign in with secret key
                    .compact();//generate the token string
            return ResponseEntity.ok(new TokenResponse(jwt));//send the token to response (sha256.payload(userinfo).key) in JSON Object
        }
        return ResponseEntity.status(401).body("Invalid username or password");//if invalid login
    }

    // DTOs
    //send login request to here(user,pass)
    static class LoginRequest {
        private String username;
        private String password;
        // getters & setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
//get the JWT token response
    static class TokenResponse {
        private String accessToken;
        public TokenResponse(String token) { this.accessToken = token; }
        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    }
}
