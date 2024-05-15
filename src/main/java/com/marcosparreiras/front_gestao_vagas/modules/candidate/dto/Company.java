package com.marcosparreiras.front_gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Company {

  private String id;
  private String name;
  private String webSite;
  private String description;
  private LocalDateTime createdAt;
  private String userName;
  private String email;
}
