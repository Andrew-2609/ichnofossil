package com.ndrewcoding.ichnofossil.service.impl;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import com.ndrewcoding.ichnofossil.model.repository.PostRepository;
import com.ndrewcoding.ichnofossil.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Page<Post> findALl(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Post> findPostsByTag(List<String> tags) {
        return postRepository.findPostsByTag(tags);
    }
}
