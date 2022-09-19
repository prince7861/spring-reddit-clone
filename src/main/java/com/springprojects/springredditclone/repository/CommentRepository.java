package com.springprojects.springredditclone.repository;

import com.springprojects.springredditclone.model.Comment;
import com.springprojects.springredditclone.model.Post;
import com.springprojects.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
