package com.shekhar.linkedInProject.postService.service;

import com.shekhar.linkedInProject.postService.entity.PostLike;
import com.shekhar.linkedInProject.postService.exception.BadRequestException;
import com.shekhar.linkedInProject.postService.exception.ResourceNotFoundException;
import com.shekhar.linkedInProject.postService.repository.PostLikeRepository;
import com.shekhar.linkedInProject.postService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final ModelMapper modelMapper;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void likePost(Long postId){
        Long userId= 1L;
        log.info("Like post of ID: {} with user of ID: {}",postId,userId);

        postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not found"));
        boolean hasAlreadyExists = postLikeRepository.existsByUserIdAndPostId(userId,postId);

        if(hasAlreadyExists) throw new BadRequestException("Already liked this post");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);


    }

    @Transactional
    public void unlikePost(Long postId){
        Long userId= 1L;
        log.info("UnLiking post of ID: {} with user of ID: {}",postId,userId);

        postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not found"));
        boolean hasAlreadyExists = postLikeRepository.existsByUserIdAndPostId(userId,postId);

        if(!hasAlreadyExists) throw new BadRequestException("Already can't unlike this post");

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);
    }

}
