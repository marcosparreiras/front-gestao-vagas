package com.marcosparreiras.front_gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class NewCompany {

  private String name;
  private String userName;
  private String webSite;
  private String email;
  private String password;
  private String confirmPassword;
  private String description;
}
