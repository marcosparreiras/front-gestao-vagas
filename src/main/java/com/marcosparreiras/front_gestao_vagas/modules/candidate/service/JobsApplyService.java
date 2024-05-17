package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import com.marcosparreiras.front_gestao_vagas.exceptions.UnauthorizedException;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.JobApplication;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobsApplyService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public JobApplication execute(String token, String jobId)
    throws UnauthorizedException {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    try {
      JobApplication jobApplication = rt.postForObject(
        this.hostAPIGestaoVagas.concat("/candidate/jobs/" + jobId + "/apply"),
        request,
        JobApplication.class
      );

      return jobApplication;
    } catch (Exception e) {
      throw new UnauthorizedException();
    }
  }
}
