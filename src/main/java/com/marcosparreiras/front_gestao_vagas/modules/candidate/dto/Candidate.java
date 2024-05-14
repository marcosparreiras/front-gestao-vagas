package com.marcosparreiras.front_gestao_vagas.modules.candidate.dto;

public class Candidate {

  private String id;
  private String name;
  private String description;
  private String userName;
  private String email;

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public String getUserName() {
    return this.userName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
