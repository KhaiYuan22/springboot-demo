package com.example.demo.auth;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.net.ssl.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OAuthTokenClient {
    public static void main(String[] args) {
        try {
            //declare the required (get from question)
            String url = "https://api.beans.my/test/sandbox/rest-soap-oauth-provider/oauth2/token";
            String clientId = "3761513680f5ef54fd97e492abecdcbc";
            String clientSecret = "c5c13156e5d8d4b4e633eeaed87a2aa7";
            String username = "user";
            String password = "pass";
            String scope = "branch";

            //disable ssl verification, for testing use(temporary)
            disableHostnameVerification();

            // Create connection to the token endpoint
            URL obj = new URL(url);// get the url as object
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();//get the connection to the url
            con.setRequestMethod("POST");//using post method
            con.setDoOutput(true);//can have output

            // Set HTTP headers
            String auth = clientId + ":" + clientSecret;//set the client id with : secret
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));//convert the id:secret to base64 format
            con.setRequestProperty("Authorization", "Basic " + encodedAuth);//add a header with authorization type with basic (base64 id:secret)
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//set the http header as content type to tell the server the format

            // Set POST body with name, password, and scope
            String body = String.format("grant_type=password&username=%s&password=%s&scope=%s",//send request to the server to get token
                    URLEncoder.encode(username, "UTF-8"),//convert special character to get correct value
                    URLEncoder.encode(password, "UTF-8"),
                    URLEncoder.encode(scope, "UTF-8"));
            try (OutputStream os = con.getOutputStream()) {//send the data to server
                os.write(body.getBytes(StandardCharsets.UTF_8));//the request, also send to server
            }

            // Get the response from the url
            int responseCode = con.getResponseCode();//get response like 200,401,404
            InputStream is = (responseCode == 200) ? con.getInputStream() : con.getErrorStream();//if the code is 200(success) then continue
            BufferedReader in = new BufferedReader(new InputStreamReader(is));//read the input
            StringBuilder response = new StringBuilder();//to store the full reply
            String inputLine;
            while ((inputLine = in.readLine()) != null) {//loop all the input
                response.append(inputLine);//get the reply
            }
            in.close();//stop input

            // Print raw response (optional, for debugging)
            System.out.println("Raw response: " + response);

            // Convert JSON to DTO, so java can get the token to use
            ObjectMapper mapper = new ObjectMapper();//Pass JSON to Java
            OAuthTokenResponse tokenResponse = mapper.readValue(response.toString(), OAuthTokenResponse.class);//read the JSON response, and convert to string (DTO)
            System.out.println("Access Token: " + tokenResponse.getAccess_token());//display token

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //disable ssl verification
    public static void disableHostnameVerification() {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);//set https (ssl) become true, so able to work the verification
    }
}
