package com.marcosparreiras.front_gestao_vagas.modules.candidate.dto;

import java.util.List;

public class Token {

  private String token;
  private List<String> roles;

  public String getToken() {
    return this.token;
  }

  public void SetToken(String token) {
    this.token = token;
  }

  public List<String> getRoles() {
    return this.roles;
  }

  public void SetToken(List<String> roles) {
    this.roles = roles;
  }
}
