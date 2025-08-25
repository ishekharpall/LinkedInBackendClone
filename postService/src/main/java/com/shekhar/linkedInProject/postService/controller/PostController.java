package com.shekhar.linkedInProject.postService.controller;

import com.shekhar.linkedInProject.postService.auth.AuthContextHolder;
import com.shekhar.linkedInProject.postService.dto.PostCreateRequestDto;
import com.shekhar.linkedInProject.postService.dto.PostDto;
import com.shekhar.linkedInProject.postService.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto, HttpServletRequest httpServletRequest){
        PostDto post = postService.createPost(postDto,1L);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        Long userId = AuthContextHolder.getCurrentUserId();
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);

    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsForUser(@PathVariable Long userId){
        List<PostDto> posts = postService.getAllPostOfUser(userId);
        return ResponseEntity.ok(posts);

    }


}
