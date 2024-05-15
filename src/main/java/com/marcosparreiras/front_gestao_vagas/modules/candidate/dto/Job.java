package com.marcosparreiras.front_gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Job {

  private String id;
  private String description;
  private String benefits;
  private String level;
  private String companyId;
  private LocalDateTime createdAt;
}
