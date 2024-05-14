package com.marcosparreiras.front_gestao_vagas.modules.candidate.controller;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Token;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.CandidateService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    HttpSession session,
    String username,
    String password
  ) {
    try {
      Token token = this.candidateService.login(username, password);

      List<SimpleGrantedAuthority> grants = token
        .getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
        .toList();

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        null,
        null,
        grants
      );

      SecurityContext securityContext = SecurityContextHolder.getContext();
      securityContext.setAuthentication(auth);
      session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
      session.setAttribute("TOKEN", token);

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
  @PreAuthorize("hasRole('CANDIDATE')")
  public String profile() {
    return "/candidate/profile";
  }
}
