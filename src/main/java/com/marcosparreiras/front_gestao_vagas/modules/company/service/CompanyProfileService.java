package com.marcosparreiras.front_gestao_vagas.modules.company.service;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyProfileService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public Company execute(String token) {
    RestTemplate rt = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    var request = new HttpEntity<>(headers);

    var result = rt.exchange(
      this.hostAPIGestaoVagas.concat("/company/"),
      HttpMethod.GET,
      request,
      Company.class
    );
    return result.getBody();
  }
}
