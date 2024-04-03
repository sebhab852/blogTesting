package com.restblog.blog.services;

import com.restblog.blog.models.Post;
import com.restblog.blog.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepo postRepo;

    @Autowired
    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public Optional<Post> getPostById(Long id) {
        return postRepo.findById(id);
    }

    public List<Post> getAllPost() {
        /*return postRepo.findAll();*/
        return postRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Post save(Post post){
        if(post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepo.save(post);
    }
}
