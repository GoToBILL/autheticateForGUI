package autotradingAuthenticate.autotrading.board.post.controller;

import autotradingAuthenticate.autotrading.board.post.dto.PostDto;
import autotradingAuthenticate.autotrading.board.post.dto.ResponsePostDto;
import autotradingAuthenticate.autotrading.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Page<ResponsePostDto>> getAllPosts(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<ResponsePostDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public void createPost(@RequestBody PostDto postDto,@AuthenticationPrincipal UserDetails userDetails) {
        postService.createPost(postDto, userDetails.getUsername());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponsePostDto> getPostById(@PathVariable(name = "postId") Long postId) {
        ResponsePostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
}
