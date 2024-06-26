package com.marcosparreiras.front_gestao_vagas.modules.company.service;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Job;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyGetJobsService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public List<Job> execute(String token) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    ParameterizedTypeReference<List<Job>> responseType = new ParameterizedTypeReference<List<Job>>() {};

    var result = rt.exchange(
      this.hostAPIGestaoVagas.concat("/company/job/"),
      HttpMethod.GET,
      request,
      responseType
    );

    List<Job> jobs = result.getBody();

    return jobs;
  }
}
