package autotradingAuthenticate.autotrading.board.post.service;

import autotradingAuthenticate.autotrading.board.post.dto.PostDto;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.post.dto.ResponsePostDto;
import autotradingAuthenticate.autotrading.board.post.entity.Post;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
import autotradingAuthenticate.autotrading.board.post.repository.PostRepository;
import autotradingAuthenticate.autotrading.exception.customException.NotFoundException;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post createPost(PostDto postDto, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_EXIST));
        Post post = new Post(postDto.getTitle(), postDto.getContent(), member);
        member.getPosts().add(post);
        return postRepository.save(post);
    }

    public Page<ResponsePostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(ResponsePostDto::new);
    }


    public ResponsePostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return new ResponsePostDto(post);
    }
}
