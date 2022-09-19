package com.springprojects.springredditclone.repository;

import com.springprojects.springredditclone.model.Post;
import com.springprojects.springredditclone.model.Subreddit;
import com.springprojects.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
