package com.marcosparreiras.front_gestao_vagas.modules.company.controller;

import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Token;
import com.marcosparreiras.front_gestao_vagas.modules.company.dto.NewCompany;
import com.marcosparreiras.front_gestao_vagas.modules.company.dto.NewJob;
import com.marcosparreiras.front_gestao_vagas.modules.company.service.CompanyCreateJobService;
import com.marcosparreiras.front_gestao_vagas.modules.company.service.CompanyCreateService;
import com.marcosparreiras.front_gestao_vagas.modules.company.service.CompanyLoginService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CompanyCreateService companyCreateService;

  @Autowired
  private CompanyLoginService companyLoginService;

  @Autowired
  private CompanyCreateJobService companyCreateJobService;

  @GetMapping("/create")
  public String create() {
    return "/company/create";
  }

  @PostMapping("/create")
  public String save(
    RedirectAttributes redirectAttributes,
    NewCompany newCompany
  ) {
    boolean isNameValid = newCompany.getName().length() >= 3;
    boolean isUserNameValid = newCompany.getUserName().length() >= 3;
    boolean isEmailValid = newCompany.getEmail().length() >= 3;
    boolean isPasswordValid = newCompany.getPassword().length() >= 6;
    boolean isPasswordsMatching = newCompany
      .getPassword()
      .equals(newCompany.getConfirmPassword());

    boolean isDataValid =
      isNameValid &&
      isUserNameValid &&
      isEmailValid &&
      isPasswordValid &&
      isPasswordsMatching;

    if (!isDataValid) {
      redirectAttributes.addFlashAttribute("errorMessage", "Invalid data");
      return "redirect:/company/create";
    }

    try {
      this.companyCreateService.execute(newCompany);
      return "redirect:/company/login";
    } catch (HttpClientErrorException e) {
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Something went wrong, try again later"
      );
      return "redirect:/company/create";
    }
  }

  @GetMapping("/login")
  public String login() {
    return "/company/login";
  }

  @PostMapping("/login")
  public String signin(
    RedirectAttributes redirectAttributes,
    HttpSession session,
    String userName,
    String password
  ) {
    try {
      Token token = this.companyLoginService.execute(userName, password);
      System.out.println(token.getToken());

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

      auth.setDetails(token.getToken());

      SecurityContext securityContext = SecurityContextHolder.getContext();
      securityContext.setAuthentication(auth);
      session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
      session.setAttribute("TOKEN", token);
      return "redirect:/company/jobs";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Invalid credentials"
      );
      return "redirect:/company/login";
    }
  }

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('COMPANY')")
  public String jobs() {
    return "/company/jobs";
  }

  @PostMapping("/jobs")
  @PreAuthorize("hasRole('COMPANY')")
  public String jobsCreate(NewJob newJob) {
    String token = this.getToken();
    this.companyCreateJobService.execute(token, newJob);

    return "redirect:/company/jobs";
  }

  private String getToken() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();
    String token = auth.getDetails().toString();
    return token;
  }
}
