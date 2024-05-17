package com.marcosparreiras.front_gestao_vagas.modules.company.service;

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
public class CompanyLoginService {

  @Value("${host.api.gestao_vagas}")
  private String hostAPIGestaoVagas;

  public Token execute(String userName, String password) {
    RestTemplate rt = new RestTemplate();
    Map<String, String> data = new HashMap<>();
    data.put("userName", userName);
    data.put("password", password);

    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(data, header);

    Token token = rt.postForObject(
      this.hostAPIGestaoVagas.concat("/company/auth"),
      request,
      Token.class
    );
    return token;
  }
}
