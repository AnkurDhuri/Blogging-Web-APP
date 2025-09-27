package com.Twitter.Tweet.Controller;

import com.Twitter.Tweet.Model.Post;
import com.Twitter.Tweet.Model.User;
import com.Twitter.Tweet.Service.PostService;
import com.Twitter.Tweet.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class WebController {

    private final UserService userService;
    private final PostService postService;

    public WebController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    //Home Page
    @GetMapping
    public String home(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    //User Registration/Profile
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Username is already taken. Please choose another one.");
            model.addAttribute("user", user);
            return "register";
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Create Post
    @GetMapping("/post/new")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "createPost";
    }

    @PostMapping("/post")
    public String savePost(@ModelAttribute Post post, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        postService.createPostByUsername(username, post);
        return "redirect:/";
    }

    //Edit Post
    @GetMapping("/post/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        model.addAttribute("post", post);
        return "editPost";
    }

    @PostMapping("/post/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post postDetails) {
        postService.updatePost(id, postDetails);
        return "redirect:/";
    }

    //Delete Post
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }

    //View User Profile
    @GetMapping("/user/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("posts", user.getPosts());

        boolean isOwner = false;
        if (principal != null) {
            isOwner = principal.getName().equals(user.getUsername());
        }
        model.addAttribute("isOwner", isOwner);
        return "profile";
    }

    @GetMapping("/profile/me")
    public String viewMyProfile(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findUserByUsername(principal.getName());

        if (user != null) {
            return "redirect:/user/" + user.getId();
        }
        return "redirect:/";
    }

    //Edit User Profile
    @GetMapping("/user/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/user/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User userDetails) {
        userService.updateUser(id, userDetails);
        return "redirect:/user/" + id;
    }

    //Delete User
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, Principal principal, HttpServletRequest request) {
        User userToDelete = userService.getUserById(id);

        if (principal != null && principal.getName().equals(userToDelete.getUsername())) {
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();

            userService.deleteUser(id);

            return "redirect:/?logout";
        }

        return "redirect:/user/" + id;
    }

    @GetMapping("/search")
    public String searchByUsername(@RequestParam String username) {
        User user = userService.findUserByUsername(username);
        if (user != null) {
            return "redirect:/user/" + user.getId();
        }
        return "redirect:/?notfound";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}
