package com.restblog.blog.repositories;

import com.restblog.blog.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<Authority, String> {
}
