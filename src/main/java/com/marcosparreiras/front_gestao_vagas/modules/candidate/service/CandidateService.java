package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandidateService {

  public void login(String username, String password) {
    RestTemplate rt = new RestTemplate();

    Map<String, String> data = new HashMap<>();
    data.put("userName", username);
    data.put("password", password);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

    var result = rt.postForObject(
      "http://localhost:8080/candidate/auth",
      request,
      String.class
    );
    System.out.println(result);
  }
}
