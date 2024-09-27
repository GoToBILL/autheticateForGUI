package autotradingAuthenticate.autotrading.board.post.repository;

import autotradingAuthenticate.autotrading.board.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.author ORDER BY p.createdDate DESC")
    List<Post> findAllWithMemberOrderByCreatedDateDesc();
}