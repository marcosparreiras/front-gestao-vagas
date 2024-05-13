package com.marcosparreiras.front_gestao_vagas.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class Home {

  @GetMapping
  public String firstPageHtml() {
    return "first-page";
  }
}
