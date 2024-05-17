package com.marcosparreiras.front_gestao_vagas.modules.company.service;

import com.marcosparreiras.front_gestao_vagas.modules.company.dto.NewJob;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyCreateJobService {

  public void execute(String token, NewJob job) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);
    HttpEntity<NewJob> request = new HttpEntity<>(job, headers);
    rt.exchange(
      "http://localhost:8080/company/job/",
      HttpMethod.POST,
      request,
      String.class
    );
  }
}
