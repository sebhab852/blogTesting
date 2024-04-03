package com.restblog.blog.config;

import com.restblog.blog.models.Account;
import com.restblog.blog.models.Authority;
import com.restblog.blog.models.Post;
import com.restblog.blog.repositories.AuthorityRepo;
import com.restblog.blog.services.AccountService;
import com.restblog.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {


    private PostService postService;
    private AccountService accountService;
    private AuthorityRepo authorityRepo;
    @Autowired
    public SeedData(PostService postService, AccountService accountService, AuthorityRepo authorityRepo) {
        this.postService = postService;
        this.accountService = accountService;
        this.authorityRepo = authorityRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAllPost();
        if(posts.isEmpty()){
            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepo.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepo.save(admin);

            Account a1 = new Account();
            Account a2 = new Account();

            a1.setEmail("a1@gmx.at");
            a1.setName("Michel");
            a1.setPassword("password");

            Set<Authority> auth1 = new HashSet<>();
            authorityRepo.findById("ROLE_USER").ifPresent(auth1::add);
            a1.setAuthorities(auth1);

            a2.setName("admin");
            a2.setEmail("admin@admin.com");
            a2.setPassword("admin");

            Set<Authority> auth2 = new HashSet<>();
            authorityRepo.findById("ROLE_ADMIN").ifPresent(auth2::add);
            authorityRepo.findById("ROLE_USER").ifPresent(auth2::add);
            a2.setAuthorities(auth2);

            accountService.saveAccount(a1);
            accountService.saveAccount(a2);


            Post p1 = new Post();
            p1.setTitle("title of post 1");
            p1.setBody("body of post 1");
            p1.setAccount(a1);

            Post p2 = new Post();
            p2.setTitle("title of post 2");
            p2.setBody("body of post 2");
            p2.setAccount(a2);
            postService.save(p1);
            postService.save(p2);
        }
    }
}
