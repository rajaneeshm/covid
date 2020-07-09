package com.evalution.covid.controller;

import com.evalution.covid.constant.UserType;
import com.evalution.covid.entity.User;
import com.evalution.covid.service.defitition.SecurityService;
import com.evalution.covid.service.defitition.UserService;
import com.evalution.covid.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()) {
            return "registration";
        }
        userForm.setUserType(UserType.END_USER);
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if(error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if(logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/"})
    public String home(Model model) {
        return "redirect:dashboard";
    }
}
