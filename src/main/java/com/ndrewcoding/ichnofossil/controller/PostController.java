package com.ndrewcoding.ichnofossil.controller;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import com.ndrewcoding.ichnofossil.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public ModelAndView getMainPage() {
        return new ModelAndView("mainPage");
    }

    @GetMapping("/posts")
    public ModelAndView getPosts(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        ModelAndView modelAndView = new ModelAndView("posts");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Post> postPage = postService.findALl(PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.DESC, "releaseDate")));

        modelAndView.addObject("postPage", postPage);

        int totalPages = postPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        return modelAndView;
    }

    @GetMapping("/posts/{id}")
    public ModelAndView getPostDetails(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("postDetails");
        Post post = postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @GetMapping("/new")
    public String getPostForm() {
        return "postForm";
    }

    @PostMapping(value = "/new")
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Check if the required fields are filled.");
            //noinspection SpringMVCViewInspection
            return "redirect:/new";
        }

        String tags = Objects.requireNonNull(result.getRawFieldValue("tags")).toString();
        String[] tag = handleTags(tags);

        post.setReleaseDate(LocalDate.now());
        post.setTags(List.of(tag));

        postService.save(post);

        return "redirect:/posts";
    }

    private String[] handleTags(String tags) {
        tags = tags.replace("[", "").replace("]", "");

        return tags.split(";");
    }
}
