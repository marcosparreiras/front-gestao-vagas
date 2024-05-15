package com.marcosparreiras.front_gestao_vagas.modules.candidate.dto;

import lombok.Data;

@Data
public class NewCandidate {

  private String name;
  private String userName;
  private String email;
  private String password;
  private String confirmPassword;
  private String description;
}
