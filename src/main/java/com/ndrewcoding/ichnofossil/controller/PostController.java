package com.ndrewcoding.ichnofossil.controller;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import com.ndrewcoding.ichnofossil.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ModelAndView getPosts() {
        ModelAndView modelAndView = new ModelAndView("posts");
        List<Post> posts = postService.findAll();
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView getPostDetails(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("postDetails");
        Post post = postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @GetMapping("/new")
    public String getPostForm() {
        return"postForm";
    }

    @PostMapping(value = "/new")
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "redirect:/new";
        }
        post.setReleaseDate(LocalDate.now());
        postService.save(post);
        return "redirect:/posts";
    }
}
