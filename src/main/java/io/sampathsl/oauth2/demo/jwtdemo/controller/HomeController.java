package io.sampathsl.oauth2.demo.jwtdemo.controller;

import io.sampathsl.oauth2.demo.jwtdemo.dto.LoginRequestDto;
import io.sampathsl.oauth2.demo.jwtdemo.util.AuthUtil;
import io.sampathsl.oauth2.demo.jwtdemo.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
public class HomeController {

  private final AuthUtil authUtil;
  private final JwtTokenUtil jwtTokenUtil;

  @GetMapping("/")
  public String basePage() {
    return "index";
  }

  @GetMapping("/home")
  public String homePage() {
    return "home";
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/access-denied")
  public String unauthorizedPage() {
    return "unauthorized";
  }

  @PostMapping("/login")
  public String login(
      @ModelAttribute @Valid @RequestBody final LoginRequestDto loginRequest,
      final BindingResult result,
      final RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) {
      result
          .getFieldErrors()
          .forEach(
              fieldError -> {
                String message = fieldError.getDefaultMessage();
                redirectAttributes.addFlashAttribute("errorMessage", message);
              });
      return "redirect:/home";
    }

    try {
      Authentication authentication =
          authUtil.authenticate(loginRequest.getUserName(), loginRequest.getPassword());
      SecurityContextHolder.getContext().setAuthentication(authentication);

      final String token = jwtTokenUtil.generateToken(authentication).replace("Bearer ", "");
      final String withOutBearerToken = token.replace("Bearer ", "");

      redirectAttributes.addFlashAttribute("token", withOutBearerToken);
      redirectAttributes.addFlashAttribute("successMessage", "Login successful!");

      return "redirect:/home";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
      return "redirect:/home";
    }
  }
}
