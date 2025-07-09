package com.example.demo.auth;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProtectedApiClient {
    public static void main(String[] args) {
        //disable SSL verification for testing
        disableHostnameVerification();

        //set the destination(API end point) and access token for able to access
        String apiUrl = "http://localhost:8080/api/v1/enrolls/grouped";
        String accessToken = "AAIgMzc2MTUxMzY4MGY1ZWY1NGZkOTdlNDkyYWJlY2RjYmP1pLwdDloVvZgrwQfuLANi1DeJEvglUYC6sg3M3WIMS8yScoVvWn4P4LOVgB9tTpj51YbRp0USfLOzOI3l3wDG";

        try {
            //Do connection
            URL url = new URL(apiUrl);//Open connection for url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();// do the connection
            con.setRequestMethod("GET"); //send by GET method Or "POST" if needed
            con.setRequestProperty("Authorization", "Bearer " + accessToken);// add authorization header and set identity to entry
            con.setRequestProperty("Accept", "application/json");//add accept header and reply with JSON format

            //read response
            int responseCode = con.getResponseCode();//get reply
            InputStream is = (responseCode == 200) ? con.getInputStream() : con.getErrorStream();//if status is 200, then get reply message, else, get error message
            BufferedReader in = new BufferedReader(new InputStreamReader(is));//read the input
            StringBuilder response = new StringBuilder();//store the reply
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);//get all reply
            }
            in.close();//stop input

            // Print the response
            System.out.println("API Response: " + response);//display what you get

            // Optional: Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.toString());//pass the JSON to string


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void disableHostnameVerification() {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

}
