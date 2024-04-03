package com.restblog.blog.controllers;

import com.restblog.blog.models.Post;
import com.restblog.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HomeController {
    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Post> posts = postService.getAllPost();
        model.addAttribute("posts", posts);
        return "home";
    }

}
