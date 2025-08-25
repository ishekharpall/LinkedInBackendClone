package com.shekhar.linkedInProject.postService.service;

import com.shekhar.linkedInProject.postService.auth.AuthContextHolder;
import com.shekhar.linkedInProject.postService.dto.PersonDto;
import com.shekhar.linkedInProject.postService.dto.PostCreateRequestDto;
import com.shekhar.linkedInProject.postService.dto.PostDto;
import com.shekhar.linkedInProject.postService.entity.Post;
import com.shekhar.linkedInProject.postService.exception.BadRequestException;
import com.shekhar.linkedInProject.postService.exception.ResourceNotFoundException;
import com.shekhar.linkedInProject.postService.feign.ConnectionsClient;
import com.shekhar.linkedInProject.postService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final ConnectionsClient connectionsClient;

    public PostDto createPost(PostCreateRequestDto postDto, long userId) {
     log.info("Creating post for user for Id: {}" , userId);
     Post post = modelMapper.map(postDto,Post.class);
     post.setUserId(userId);
     post = postRepository.save(post);
     return modelMapper.map(post,PostDto.class);
    }

    public PostDto getPostById (Long postId){
    log.info("Getting the post with Id: {}",postId);

        Long userId = AuthContextHolder.getCurrentUserId();

        List<PersonDto> personDtoList = connectionsClient.getFirstDegreeConnections(userId);

//    Post post = postRepository.findById(postId).orElseThrow(() ->
//            new ResourceNotFoundException("Post not found "));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post not found " +
                "with ID: "+postId));
      return modelMapper.map(post,PostDto.class);
    }

    public List<PostDto> getAllPostOfUser(Long userId){
        log.info("Getting all post for user with Id: {}" , userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map((element)->modelMapper.map(element,PostDto.class))
                .collect(Collectors.toList());
    }

}
