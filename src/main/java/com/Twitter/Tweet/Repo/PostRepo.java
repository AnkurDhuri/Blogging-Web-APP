package com.Twitter.Tweet.Repo;

import com.Twitter.Tweet.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
}

