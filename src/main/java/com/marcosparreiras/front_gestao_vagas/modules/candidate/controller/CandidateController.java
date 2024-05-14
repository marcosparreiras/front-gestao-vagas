package com.marcosparreiras.front_gestao_vagas.modules.candidate.controller;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @GetMapping("/login")
  public String login() {
    return "/candidate/login";
  }

  @PostMapping("/sing-in")
  public String singIn(
    RedirectAttributes redirectAttributes,
    String username,
    String password
  ) {
    try {
      this.candidateService.login(username, password);
      return "redirect:/candidate/profile";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Invalid credentials"
      );
      return "redirect:/candidate/login";
    }
  }

  @GetMapping("/profile")
  public String profile() {
    return "/candidate/profile";
  }
}
