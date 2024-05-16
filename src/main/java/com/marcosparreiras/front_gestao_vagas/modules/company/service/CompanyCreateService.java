package com.marcosparreiras.front_gestao_vagas.modules.company.service;

import com.marcosparreiras.front_gestao_vagas.modules.company.dto.NewCompany;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyCreateService {

  public void execute(NewCompany company) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<NewCompany> request = new HttpEntity<>(company, headers);

    rt.exchange(
      "http://localhost:8080/company/",
      HttpMethod.POST,
      request,
      Object.class
    );
  }
}
