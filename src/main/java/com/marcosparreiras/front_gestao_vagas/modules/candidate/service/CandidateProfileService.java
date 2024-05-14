package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Candidate;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandidateProfileService {

  public Candidate execute(String token) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    var result = rt.exchange(
      "http://localhost:8080/candidate/profile",
      HttpMethod.GET,
      request,
      Candidate.class
    );

    Candidate candidate = result.getBody();

    return candidate;
  }
}
