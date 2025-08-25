package com.shekhar.linkedInProject.postService.repository;

import com.shekhar.linkedInProject.postService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
