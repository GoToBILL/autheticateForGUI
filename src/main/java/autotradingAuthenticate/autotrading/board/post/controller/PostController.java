package autotradingAuthenticate.autotrading.board.post.controller;

import autotradingAuthenticate.autotrading.board.post.dto.PostDto;
import autotradingAuthenticate.autotrading.board.post.dto.ResponsePostDto;
import autotradingAuthenticate.autotrading.board.post.entity.Post;
import autotradingAuthenticate.autotrading.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postService.createPost(postDto, username);
        // 걍 세션에서 username 빼와도 되잖아.
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<ResponsePostDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponsePostDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }
}