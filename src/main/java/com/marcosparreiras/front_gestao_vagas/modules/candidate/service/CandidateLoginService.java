package com.marcosparreiras.front_gestao_vagas.modules.candidate.service;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Token;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandidateLoginService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public Token execute(String username, String password) {
    RestTemplate rt = new RestTemplate();

    Map<String, String> data = new HashMap<>();
    data.put("userName", username);
    data.put("password", password);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

    Token result = rt.postForObject(
      this.hostAPIGestaoVagas.concat("/candidate/auth"),
      request,
      Token.class
    );
    return result;
  }
}
