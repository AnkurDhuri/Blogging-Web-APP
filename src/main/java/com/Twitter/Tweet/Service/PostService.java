package com.Twitter.Tweet.Service;

import com.Twitter.Tweet.Model.Post;
import com.Twitter.Tweet.Model.User;
import com.Twitter.Tweet.Repo.PostRepo;
import com.Twitter.Tweet.Repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public Post createPostByUsername(String username, Post post) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found for email: " + username);
        }
        post.setUser(user);
        return postRepo.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    public Optional<Post> getPostById(long id) {
        return postRepo.findById(id);
    }

    public Post updatePost(Long postId, Post postDetails) {
        return postRepo.findById(postId).map(post -> {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            return postRepo.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }
}