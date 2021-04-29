package com.ndrewcoding.ichnofossil.controller;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import com.ndrewcoding.ichnofossil.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {

    static final String POSTS_URL = "/posts";

    @Autowired
    MockMvc mvc;

    @MockBean
    PostService postService;

    @Test
    @DisplayName("Must return all the Posts in the view")
    public void getPostsTest() throws Exception {
        List<Post> posts = createNewPostList();

        BDDMockito.given(postService.findAll()).willReturn(posts);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(POSTS_URL)
                .accept(MediaType.TEXT_HTML);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(content().string(containsString(posts.get(0).getAuthor())))
                .andExpect(content().string(containsString(posts.get(0).getTitle())))
                .andExpect(content().string(containsString(posts.get(0).getText())))
                .andExpect(content().string(containsString(posts.get(0).getReleaseDate().toString())));
    }

    @Test
    @DisplayName("Must return a Post view with its given ID")
    public void getPostDetailsTest() throws Exception {
        Post post = createNewPostList().get(0);

        Long generic_id = 1L;

        BDDMockito.given(postService.findById(generic_id)).willReturn(Optional.of(post));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(POSTS_URL.concat("/" + generic_id))
                .accept(MediaType.TEXT_HTML);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("postDetails"))
                .andExpect(content().string(containsString(post.getAuthor())))
                .andExpect(content().string(containsString(post.getTitle())))
                .andExpect(content().string(containsString(post.getText())))
                .andExpect(content().string(containsString(post.getReleaseDate().toString())));
    }

    @Test
    @DisplayName("Must return 404 when Post is not found")
    public void getNonexistentPostDetailsTest() throws Exception {
        BDDMockito.given(postService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(POSTS_URL.concat("/" + 1L))
                .accept(MediaType.TEXT_HTML);

        mvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Must return the New Post view")
    public void getPostFormTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(POSTS_URL.concat("/new"))
                .accept(MediaType.TEXT_HTML);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("postForm"));
    }

    @Test
    @DisplayName("Must successfully create a new Post")
    public void createPostTest() throws Exception {
        Post post = createNewPostList().get(0);

        BDDMockito.given(postService.save(Mockito.any(Post.class))).willReturn(post);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(POSTS_URL.concat("/new"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("title", post.getTitle())
                .param("author", post.getAuthor())
                .param("text", post.getText());

        mvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/posts"));
    }

    private List<Post> createNewPostList() {
        return List.of(Post.builder().id(1L).author("Andrew").title("My Studies").releaseDate(LocalDate.now()).text("Content").build());
    }

}
