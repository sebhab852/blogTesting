package com.restblog.blog.controllers;

import com.restblog.blog.models.Account;
import com.restblog.blog.models.Post;
import com.restblog.blog.services.AccountService;
import com.restblog.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;
    private final AccountService accountService;

    @Autowired
    public PostController(PostService postService, AccountService accountService) {
        this.postService = postService;
        this.accountService = accountService;
    }

    @GetMapping("/posts/insight/{id}")
    public String getPost(@PathVariable(required = true) Long id, Model model){
        Optional<Post> post = postService.getPostById(id);
        if(post.isPresent()){
            model.addAttribute("post",post.get());
            return "post";
        }
        return "404";
    }

    @GetMapping("/posts/new")
    public String createNewPost(Model model){
        Optional<Account> optAcc = accountService.findByEmail("admin@admin.com");
        if(optAcc.isPresent()){
            Post post = new Post();
            post.setAccount(optAcc.get());
            model.addAttribute("post",post);
            return "post_new";
        }
        else {
            return "404";
        }
    }

    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String saveNewPost(@ModelAttribute Post post, Principal principal){
        String authUsername;
        if (principal != null) {
            authUsername = principal.getName();
        } else {
            authUsername = "anonymousUser";
        }
        Account account = accountService.findByEmail(authUsername).orElseThrow(() -> new IllegalArgumentException("No Accoount with email " +
                authUsername + " found"));
        post.setAccount(account);
        postService.save(post);
        return "redirect:/";
    }
}
