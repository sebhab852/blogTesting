package com.restblog.blog.controllers;

import com.restblog.blog.models.Account;
import com.restblog.blog.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final AccountService accountService;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        Account account = new Account();
        model.addAttribute("account",account);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewAccount(@ModelAttribute Account account){
        accountService.saveAccount(account);
        return "redirect:/";
    }
}
