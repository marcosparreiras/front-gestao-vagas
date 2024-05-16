package com.marcosparreiras.front_gestao_vagas.modules.company.controller;

import com.marcosparreiras.front_gestao_vagas.modules.company.dto.NewCompany;
import com.marcosparreiras.front_gestao_vagas.modules.company.service.CompanyCreateService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
