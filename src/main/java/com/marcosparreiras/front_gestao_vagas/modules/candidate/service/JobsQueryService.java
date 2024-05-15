package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Job;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JobsQueryService {

  public List<Job> execute(String filter, String token) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    ParameterizedTypeReference<List<Job>> responseType = new ParameterizedTypeReference<List<Job>>() {};

    UriComponentsBuilder uriBuilder = UriComponentsBuilder
      .fromHttpUrl("http://localhost:8080/candidate/jobs")
      .queryParam("filter", filter);

    var result = rt.exchange(
      uriBuilder.toUriString(),
      HttpMethod.GET,
      request,
      responseType
    );

    List<Job> jobs = result.getBody();

    return jobs;
  }
}
