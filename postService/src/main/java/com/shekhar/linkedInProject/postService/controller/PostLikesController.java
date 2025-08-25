package com.shekhar.linkedInProject.postService.controller;

import com.shekhar.linkedInProject.postService.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    ResponseEntity<Void> likePost(@PathVariable Long postId){
       postLikeService.likePost(postId);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> unlikePost(@PathVariable Long postId){
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }




}
