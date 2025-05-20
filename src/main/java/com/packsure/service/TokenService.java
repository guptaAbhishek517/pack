
package com.packsure.service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class TokenService {
   private final RestTemplate restTemplate = new RestTemplate();
  
   private String token;
   private Instant tokenExpiryTime;
  
   public synchronized String getToken() {
   	
   	 // Check if token is not null and not expired
       if (token != null && Instant.now().isBefore(tokenExpiryTime)) {
           return token;
       }
      
       String authUrl = "https://apiv2.shiprocket.in/v1/external/auth/login";
       Map<String, String> credentials = Map.of("email", "sagarbansal1905@gmail.com", "password", "Dt1r!$H%@XrZ1dQZ");
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       HttpEntity<Map<String, String>> request = new HttpEntity<>(credentials, headers);
       ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, request, Map.class);
      
       this.token = (String) response.getBody().get("token");
       
       System.out.println(token);
       // Set expiry to 9.5 days just to be safe
       this.tokenExpiryTime = Instant.now().plus(9, ChronoUnit.DAYS).plus(12, ChronoUnit.HOURS);
       return token;
   }
}

