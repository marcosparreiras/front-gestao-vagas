package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.NewCandidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandidateCreateService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public void execute(NewCandidate candidate) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<NewCandidate> request = new HttpEntity<>(candidate, headers);

    rt.exchange(
      hostAPIGestaoVagas.concat("/candidate/"),
      HttpMethod.POST,
      request,
      Object.class
    );
  }
}
