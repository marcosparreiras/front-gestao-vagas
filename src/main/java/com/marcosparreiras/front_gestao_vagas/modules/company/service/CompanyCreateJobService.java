package com.marcosparreiras.front_gestao_vagas.modules.company.service;

import com.marcosparreiras.front_gestao_vagas.modules.company.dto.NewJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyCreateJobService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public void execute(String token, NewJob job) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);
    HttpEntity<NewJob> request = new HttpEntity<>(job, headers);
    rt.exchange(
      this.hostAPIGestaoVagas.concat("/company/job/"),
      HttpMethod.POST,
      request,
      String.class
    );
  }
}
