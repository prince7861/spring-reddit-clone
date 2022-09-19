package com.springprojects.springredditclone.repository;

import com.springprojects.springredditclone.model.Post;
import com.springprojects.springredditclone.model.User;
import com.springprojects.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
