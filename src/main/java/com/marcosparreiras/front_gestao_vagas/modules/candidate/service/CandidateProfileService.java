package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import com.marcosparreiras.front_gestao_vagas.exceptions.UnauthorizedException;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Candidate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandidateProfileService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public Candidate execute(String token) throws UnauthorizedException {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    try {
      var result = rt.exchange(
        this.hostAPIGestaoVagas.concat("/candidate/profile"),
        HttpMethod.GET,
        request,
        Candidate.class
      );

      Candidate candidate = result.getBody();

      return candidate;
    } catch (Exception e) {
      throw new UnauthorizedException();
    }
  }
}
