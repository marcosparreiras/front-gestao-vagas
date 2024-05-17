package com.marcosparreiras.front_gestao_vagas.modules.candidate.controller;

import com.marcosparreiras.front_gestao_vagas.exceptions.UnauthorizedException;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Candidate;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Job;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.JobApplication;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.NewCandidate;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.dto.Token;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.CandidateCreateService;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.CandidateLoginService;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.CandidateProfileService;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.JobsApplyService;
import com.marcosparreiras.front_gestao_vagas.modules.candidate.service.JobsQueryService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateLoginService candidateLoginService;

  @Autowired
  private CandidateProfileService candidateProfileService;

  @Autowired
  private CandidateCreateService candidateCreateService;

  @Autowired
  private JobsQueryService jobsQueryService;

  @Autowired
  private JobsApplyService jobsApplyService;

  @GetMapping("/create")
  public String create() {
    return "/candidate/create";
  }

  @PostMapping("/create")
  public String save(
    RedirectAttributes redirectAttributes,
    NewCandidate newCandidate
  ) {
    boolean isNameValid = newCandidate.getName().length() >= 3;
    boolean isUserNameValid = newCandidate.getUserName().length() >= 3;
    boolean isEmailValid = newCandidate.getEmail().length() >= 3;
    boolean isPasswordValid = newCandidate.getPassword().length() >= 6;
    boolean isPasswordsMatching = newCandidate
      .getPassword()
      .equals(newCandidate.getConfirmPassword());

    boolean isDataValid =
      isNameValid &&
      isUserNameValid &&
      isEmailValid &&
      isPasswordValid &&
      isPasswordsMatching;

    if (!isDataValid) {
      redirectAttributes.addFlashAttribute("errorMessage", "Invalid data");
      return "redirect:/candidate/create";
    }

    try {
      this.candidateCreateService.execute(newCandidate);
      return "redirect:/candidate/login";
    } catch (HttpClientErrorException e) {
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Something went wrong, try again later"
      );
      return "redirect:/candidate/create";
    }
  }

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
      Token token = this.candidateLoginService.execute(username, password);

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
  public String profile(Model model) {
    try {
      String token = this.getToken();
      Candidate candidate = this.candidateProfileService.execute(token);
      model.addAttribute("candidate", candidate);
      return "/candidate/profile";
    } catch (UnauthorizedException e) {
      return "redirect:/candidate/login";
    }
  }

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  public String jobs(Model model, String filter) {
    try {
      String token = this.getToken();
      System.out.println(filter);
      filter = filter != null ? filter : "";

      List<Job> jobs = this.jobsQueryService.execute(filter, token);
      model.addAttribute("jobs", jobs);

      return "/candidate/jobs";
    } catch (UnauthorizedException e) {
      return "redirect:/candidate/login";
    }
  }

  @PostMapping("/jobs/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  public String jobsApply(String jobId) {
    try {
      String token = this.getToken();

      JobApplication jobApllication =
        this.jobsApplyService.execute(token, jobId);

      System.out.println(jobApllication);

      return "redirect:/candidate/jobs";
    } catch (UnauthorizedException e) {
      return "redirect:/candidate/login";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(null);
    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    session.setAttribute("TOKEN", null);
    return "redirect:/candidate/login";
  }

  private String getToken() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();
    String token = auth.getDetails().toString();
    return token;
  }
}
